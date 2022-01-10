package com.lpwanw.appui.controller;

import com.lpwanw.appui.Entity.*;
import com.lpwanw.appui.reponsitory.DanhSachDangKyRepository;
import com.lpwanw.appui.reponsitory.KhoaThiRepository;
import com.lpwanw.appui.reponsitory.PhongThiRepository;
import com.lpwanw.appui.reponsitory.TrinhDoRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class PhongThiController implements Initializable {
    public ComboBox<KhoaThi> KhoaThiCombo;
    public ComboBox<TrinhDo> trinhdoCombo;
    public ComboBox<Integer> begin;
    public Label end;
    public Label ngayThi;
    public Label tenPhong;
    public Button addButton;
    public TableView<PhongThi> table;
    public TableColumn<PhongThi, String> tenColumn;
    public TableColumn<PhongThi, TrinhDo> trinhDoColumn;
    public TableColumn<PhongThi, String> beginColumn;
    public TableColumn<PhongThi, String> endColumn;
    public TableView<SoBaoDanh> danhsachThiSinh;
    public TableColumn<SoBaoDanh, String> sbdColumn;
    public TableColumn<SoBaoDanh, Student> tenthiSinhColumn;
    public TableColumn<SoBaoDanh, Student> cmndColumn;
    ObservableList<KhoaThi> listKhoaThi;
    ObservableList<TrinhDo> listTrinhDo;
    ObservableList<Integer> listGio;
    ObservableList<PhongThi> listPhongThi;
    @Autowired
    KhoaThiRepository khoaThiRepository;
    @Autowired
    TrinhDoRepository trinhDoRepository;
    @Autowired
    PhongThiRepository phongThiRepository;
    @Autowired
    DanhSachDangKyRepository danhSachDangKyRepository;
    KhoaThi lastKhoaThi;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listKhoaThi = FXCollections.observableList(khoaThiRepository.findAll());
        listTrinhDo = FXCollections.observableList(trinhDoRepository.findAll());
        listGio = FXCollections.observableList(Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24));
        begin.setItems(listGio);
        begin.getSelectionModel().select(12);
        end.setText("14:15");
        KhoaThiCombo.setItems(listKhoaThi);
        KhoaThiCombo.getSelectionModel().selectLast();
        KhoaThiCombo.setConverter(new StringConverter<>() {
            @Override
            public String toString(KhoaThi khoaThi) {
                return khoaThi.getId()+":"+khoaThi.getTen() +": "+khoaThi.getNgaythi();
            }

            @Override
            public KhoaThi fromString(String s) {
                return khoaThiRepository.getById(Integer.valueOf(s.substring(s.indexOf(':'))));
            }
        });
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
        ngayThi.setText(khoaThiRepository.findAll().get(listKhoaThi.size()-1).getNgaythi()+"");
        lastKhoaThi = khoaThiRepository.getTopByNgaythiIsAfter(LocalDate.now());
        if(lastKhoaThi==null){
            addButton.setDisable(true);
        }
        listPhongThi = FXCollections.observableList(phongThiRepository.getPhongThisByKhoathi(lastKhoaThi));
        tenPhong.setText(getTenPhongThi());
        tenColumn.setCellValueFactory(new PropertyValueFactory<>("tenphong"));
        trinhDoColumn.setCellValueFactory(new PropertyValueFactory<>("trinhdo"));
        trinhDoColumn.setCellFactory(cell-> new TableCell<>(){
            @Override
            protected void updateItem(TrinhDo item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(item.getTen());
                }
            }
        });
        beginColumn.setCellValueFactory(new PropertyValueFactory<>("batDau"));
        beginColumn.setCellFactory(cell-> new TableCell<>(){
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(item+":00");
                }
            }
        });
        endColumn.setCellValueFactory(new PropertyValueFactory<>("ketThuc"));
        table.setItems(listPhongThi);
        sbdColumn.setCellValueFactory(new PropertyValueFactory<>("soBaoDanh"));
        tenthiSinhColumn.setCellValueFactory(new PropertyValueFactory<>("nguoidangky"));
        tenthiSinhColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Student item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(item.getHoTen());
                }
            }
        });
        cmndColumn.setCellValueFactory(new PropertyValueFactory<>("nguoidangky"));
        cmndColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Student item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(item.getCmnd());
                }
            }
        });
    }
    private String getTenPhongThi(){
        return trinhdoCombo.getSelectionModel().getSelectedItem().getTen()+"P"+String.format("%02d",listPhongThi.size()+1);
    }

    public void onTrinhDoChanged(ActionEvent actionEvent) {
        tenPhong.setText(getTenPhongThi());
    }

    public void onBeginChanged(ActionEvent actionEvent) {
        end.setText(begin.getSelectionModel().getSelectedItem()+2+":15");
    }

    public void onAddPhongThi(ActionEvent actionEvent) {
        PhongThi entity = new PhongThi();
        entity.setTenphong(tenPhong.getText());
        entity.setTrinhdo(trinhdoCombo.getSelectionModel().getSelectedItem());
        entity.setKhoathi(lastKhoaThi);
        entity.setBatDau(begin.getSelectionModel().getSelectedItem()+"");
        entity.setKetThuc(end.getText());
        phongThiRepository.save(entity);
        listPhongThi = FXCollections.observableList(phongThiRepository.getPhongThisByKhoathi(lastKhoaThi));
        tenPhong.setText(getTenPhongThi());
        table.setItems(listPhongThi);
    }

    public void refresh(ActionEvent actionEvent) {
        lastKhoaThi = khoaThiRepository.getTopByNgaythiIsAfter(LocalDate.now());
        if(lastKhoaThi==null){
            addButton.setDisable(true);
            return;
        }
        listPhongThi = FXCollections.observableList(phongThiRepository.getPhongThisByKhoathi(lastKhoaThi));
        tenPhong.setText(getTenPhongThi());
        table.setItems(listPhongThi);
    }

    public void onTableClick(MouseEvent mouseEvent) {
        PhongThi pt = table.getSelectionModel().getSelectedItem();
        if(pt==null) return;
        Set<DanhSachDangKy> list = pt.getDanhsach();
        List<SoBaoDanh> listSBD = new ArrayList<>();
        list.forEach(e->{
            listSBD.add(e.getSbd());
        });
        danhsachThiSinh.setItems(FXCollections.observableList(listSBD));
    }

    public void addMarkAction(ActionEvent actionEvent) {
        PhongThi pt = table.getSelectionModel().getSelectedItem();
        if(pt==null) return;
        Set<DanhSachDangKy> list = pt.getDanhsach();
        list.forEach(e->{
            Dialog<Diem> dialog =  new Dialog();
            dialog.setTitle("Nhập điểm");
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("/fxml/add_mark.fxml"));
            Pane pane;
            try {
                pane = fxml.load();
                dialog.getDialogPane().setContent(pane);
                AddMarkController con = fxml.getController();
                con.tenthisinh.setText(e.getSbd().getNguoidangky().getHoTen());
                con.cmnd.setText(e.getSbd().getNguoidangky().getCmnd());
                con.sdb.setText(e.getSbd().getSoBaoDanh());
                dialog.setResultConverter(dialogButton->{
                    if(dialogButton == ButtonType.OK){
                        try{
                            Diem d = new Diem();
                            d.setDoc(Integer.parseInt(con.doc.getText()));
                            d.setNghe(Integer.parseInt(con.nghe.getText()));
                            d.setNoi(Integer.parseInt(con.noi.getText()));
                            d.setViet(Integer.parseInt(con.viet.getText()));
                            return d;
                        }catch (NumberFormatException ex){
                            throw new NumberFormatException("Chịu");
                        }
                    }
                    Diem d = new Diem();
                    d.setDoc(-1);
                    d.setNghe(-1);
                    d.setNoi(-1);
                    d.setViet(-1);
                    return d;
                });
                Optional<Diem> result = dialog.showAndWait();
                if (result.isPresent()){
                    e.setDoc(result.get().getDoc());
                    e.setNghe(result.get().getNghe());
                    e.setNoi(result.get().getNoi());
                    e.setViet(result.get().getViet());
                    danhSachDangKyRepository.save(e);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    public void onChanged(ActionEvent actionEvent) {
        listPhongThi = FXCollections.observableList(phongThiRepository.getPhongThisByKhoathi(KhoaThiCombo.getSelectionModel().getSelectedItem()));
        table.setItems(listPhongThi);
    }
}
