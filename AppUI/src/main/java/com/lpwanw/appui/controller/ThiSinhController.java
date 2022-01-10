package com.lpwanw.appui.controller;

import com.lpwanw.appui.Entity.*;
import com.lpwanw.appui.reponsitory.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
public class ThiSinhController implements Initializable {
    public ComboBox<TrinhDo> trinhdoCombo;
    public TableView<Student> table;
    public TableColumn<Student, Integer> idCoumn;
    public TableColumn<Student, String> tenColumn;
    public TableColumn<Student, LocalDate> ngaySinhColumn;
    public TableColumn<Student, String> cmndColumn;
    public Label tenText;
    public Label cmndText;
    public TableColumn<Student, String> sdtColumn;
    public TextField tenSearch;
    public TextField sdtSearch;
    public Label sbdText;
    public Label sbdText2;
    public GridPane gridLayout1;
    public GridPane gridLayout2;
    public Label phongThiText;
    public Label nghe;
    public Label noi;
    public Label doc;
    public Label viet;
    public Label phongThiText1;
    public Label nghe1;
    public Label noi1;
    public Label doc1;
    public Label viet1;
    public Label khoaThiText;
    public Button dangkyButton;
    @Autowired
    SoBaoDanhRepository soBaoDanhRepository;
    @Autowired
    StudentRepository thiSinhRepository;
    @Autowired
    KhoaThiRepository khoaThiRepository;
    @Autowired
    TrinhDoRepository trinhDoRepository;
    @Autowired
    DanhSachDangKyRepository danhSachDangKyRepository;
    @Autowired
    PhongThiRepository phongThiRepository;
    ObservableList<KhoaThi> listKhoaThi;
    ObservableList<TrinhDo> listTrinhDo;
    ObservableList<Student> listStudent;
    KhoaThi lastKhoaThi;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listStudent = FXCollections.observableList(thiSinhRepository.findAll());
        listKhoaThi = FXCollections.observableList(khoaThiRepository.findAll());
        listTrinhDo = FXCollections.observableList(trinhDoRepository.findAll());
        idCoumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        tenColumn.setCellValueFactory(new PropertyValueFactory<>("hoTen"));
        ngaySinhColumn.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
        cmndColumn.setCellValueFactory(new PropertyValueFactory<>("cmnd"));
        sdtColumn.setCellValueFactory(new PropertyValueFactory<>("sdt"));
        lastKhoaThi = khoaThiRepository.getTopByNgaythiIsAfter(LocalDate.now());
        if(lastKhoaThi!=null){
            khoaThiText.setText(lastKhoaThi.getTen()+": "+lastKhoaThi.getNgaythi());
            dangkyButton.setDisable(false);
        }else{
            dangkyButton.setDisable(true);
        }
        table.setItems(listStudent);
        trinhdoCombo.setItems(listTrinhDo);
        trinhdoCombo.getSelectionModel().selectFirst();
        trinhdoCombo.setConverter(new StringConverter<>() {
            @Override
            public String toString(TrinhDo trinhDo) {
                return trinhDo.getTen();
            }

            @Override
            public TrinhDo fromString(String s) {
                return trinhDoRepository.findByTen(s);
            }
        });
    }

    public void onSearch(KeyEvent keyEvent) {
        String ten = tenSearch.getText();
        String sdt = sdtSearch.getText();
        listStudent = FXCollections.observableList(thiSinhRepository.findAllByHoTenContainsAndSdtContains(ten,sdt));
        table.setItems(listStudent);
    }

    public void onAddThiSinh(ActionEvent actionEvent) {
        Dialog<Student> dialog =  new Dialog();
        dialog.setTitle("Thêm thí sinh mới");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK,ButtonType.CANCEL);
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/fxml/add_thi_sinh.fxml"));
        Pane pane;
        try {
            pane = fxml.load();
            dialog.getDialogPane().setContent(pane);
            AddThiSinhController con = fxml.getController();

            dialog.setResultConverter(dialogButton->{
                if(dialogButton == ButtonType.OK){
                    try{
                        Student st = new Student();
                        st.setHoTen(con.nameText.getText());
                        st.setCmnd(con.cmndText.getText());
                        st.setNgaySinh(con.ngaySinh.getValue());
                        st.setSdt(con.sdtText.getText());
                        return st;
                    }catch (NumberFormatException ex){
                        throw new NumberFormatException("Chịu");
                    }
                }
                return null;
            });
            Optional<Student> result = dialog.showAndWait();
            if (result.isPresent()){
                try {
                    thiSinhRepository.save(result.get());
                }catch (Exception e){
                    System.out.println("Không thể tạo");
                }
                listStudent = FXCollections.observableList(thiSinhRepository.findAll());
                table.setItems(listStudent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onTableClick(MouseEvent mouseEvent) {
        Student st = table.getSelectionModel().getSelectedItem();
        if(st==null) return;
        tenText.setText(st.getHoTen());
        cmndText.setText(st.getCmnd());
        List<SoBaoDanh> listsbd = soBaoDanhRepository.findSoBaoDanhByNguoidangkyAndKhoaThi(st,lastKhoaThi);
        SoBaoDanh s1;
        SoBaoDanh s2;
        if(listsbd.size()==0){
            gridLayout1.setVisible(false);
            gridLayout2.setVisible(false);
            return;
        }
        if(listsbd.size()==1){
            gridLayout1.setVisible(true);
            s1 = listsbd.get(0);
            sbdText.setText(s1.getSoBaoDanh());
            DanhSachDangKy danhSachDangKy = danhSachDangKyRepository.findDanhSachDangKyBySbd(s1);
            phongThiText.setText(danhSachDangKy.getPhongthi().getTenphong());
            nghe.setText(danhSachDangKy.getNghe()==-1?"Chưa có điểm thi":danhSachDangKy.getNghe()+"");
            noi.setText(danhSachDangKy.getNoi()==-1?"Chưa có điểm thi":danhSachDangKy.getNoi()+"");
            doc.setText(danhSachDangKy.getDoc()==-1?"Chưa có điểm thi":danhSachDangKy.getDoc()+"");
            viet.setText(danhSachDangKy.getViet()==-1?"Chưa có điểm thi":danhSachDangKy.getViet()+"");
            gridLayout2.setVisible(false);
        }
        if(listsbd.size()==2){
            gridLayout1.setVisible(true);
            gridLayout2.setVisible(true);
            s1 = listsbd.get(0);
            sbdText.setText(s1.getSoBaoDanh());
            s2 = listsbd.get(1);
            sbdText2.setText(s2.getSoBaoDanh());
            DanhSachDangKy danhSachDangKy = danhSachDangKyRepository.findDanhSachDangKyBySbd(s1);
            DanhSachDangKy danhSachDangKy1 = danhSachDangKyRepository.findDanhSachDangKyBySbd(s2);
            phongThiText.setText(danhSachDangKy.getPhongthi().getTenphong());
            nghe.setText(danhSachDangKy.getNghe()==-1?"Chưa có điểm thi":danhSachDangKy.getNghe()+"");
            noi.setText(danhSachDangKy.getNoi()==-1?"Chưa có điểm thi":danhSachDangKy.getNoi()+"");
            doc.setText(danhSachDangKy.getDoc()==-1?"Chưa có điểm thi":danhSachDangKy.getDoc()+"");
            viet.setText(danhSachDangKy.getViet()==-1?"Chưa có điểm thi":danhSachDangKy.getViet()+"");
            phongThiText1.setText(danhSachDangKy1.getPhongthi().getTenphong());
            nghe1.setText(danhSachDangKy1.getNghe()==-1?"Chưa có điểm thi":danhSachDangKy1.getNghe()+"");
            noi1.setText(danhSachDangKy1.getNoi()==-1?"Chưa có điểm thi":danhSachDangKy1.getNoi()+"");
            doc1.setText(danhSachDangKy1.getDoc()==-1?"Chưa có điểm thi":danhSachDangKy1.getDoc()+"");
            viet1.setText(danhSachDangKy1.getViet()==-1?"Chưa có điểm thi":danhSachDangKy1.getViet()+"");
        }
    }

    public void onDangKyThi(ActionEvent actionEvent) {
        Student st = table.getSelectionModel().getSelectedItem();
        if(st==null) return;
        TrinhDo td = trinhdoCombo.getSelectionModel().getSelectedItem();
        String SBD = td.getTen()+String.format("%02d",soBaoDanhRepository.findSoBaoDanhByKhoaThiAndTrinhDo(lastKhoaThi,td).size()+1);
        SoBaoDanh soBaoDanh = new SoBaoDanh();
        soBaoDanh.setSoBaoDanh(SBD);
        soBaoDanh.setKhoaThi(lastKhoaThi);
        soBaoDanh.setTrinhDo(td);
        soBaoDanh.setNguoidangky(st);
        List<PhongThi> listPhong  = phongThiRepository.findPhongThiByKhoathiAndTrinhdo(lastKhoaThi,td);
        try {
            soBaoDanh = soBaoDanhRepository.save(soBaoDanh);
            System.out.println("Tạo thành công");
        }catch (Exception e){
            System.err.println("Không thể đăng ký thêm");
            return;
        }
        for (PhongThi p: listPhong) {
            try {
                if(p.getDanhsach().size()<30){
                    DanhSachDangKy dsdk = new DanhSachDangKy();
                    dsdk.setPhongthi(p);
                    dsdk.setSbd(soBaoDanh);
                    dsdk.setNghe(-1);
                    dsdk.setDoc(-1);
                    dsdk.setNoi(-1);
                    dsdk.setViet(-1);
                    danhSachDangKyRepository.save(dsdk);
                    break;
                }
                System.out.println("Tạo thành công");
            }catch (Exception e){
                System.err.println("Không thể đăng ký thêm");
                return;
            }

        }
    }

    public void refresh(ActionEvent actionEvent) {
        lastKhoaThi = khoaThiRepository.getTopByNgaythiIsAfter(LocalDate.now());
        if(lastKhoaThi!=null){
            khoaThiText.setText(lastKhoaThi.getTen()+": "+lastKhoaThi.getNgaythi());
            dangkyButton.setDisable(false);
        }else{
            dangkyButton.setDisable(true);
        }
        listStudent = FXCollections.observableList(thiSinhRepository.findAll());
        table.setItems(listStudent);
    }
}
