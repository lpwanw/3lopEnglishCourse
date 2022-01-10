package com.lpwanw.appui.controller;

import com.lpwanw.appui.Entity.KhoaThi;
import com.lpwanw.appui.reponsitory.KhoaThiRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

@Component
public class KhoaThiController implements Initializable {
    public TableView<KhoaThi> table;
    public TableColumn<KhoaThi, Integer> idColumn;
    public TableColumn<KhoaThi, String> tenColumn;
    public TableColumn<KhoaThi, LocalDate> ngaythiColumn;
    public DatePicker ngaythi;
    public TextField tenText;
    @Autowired
    KhoaThiRepository khoaThiRepository;
    ObservableList<KhoaThi> list;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        list = FXCollections.observableList(khoaThiRepository.findAll());
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        tenColumn.setCellValueFactory(new PropertyValueFactory<>("ten"));
        ngaythiColumn.setCellValueFactory(new PropertyValueFactory<>("ngaythi"));
        ngaythiColumn.setCellFactory(column -> new TableCell<>() {
            private final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(format.format(item));
                }
            }
        });
        table.setItems(list);
    }

    public void onAddAction(ActionEvent actionEvent) {
        if(ngaythi.getValue()==null) return;
        if(tenText.getText().equals("")) return;
        if(ngaythi.getValue().isBefore(LocalDate.now())) return;
        KhoaThi kt = khoaThiRepository.getTopByNgaythiIsAfter(LocalDate.now());
        if(kt!=null) return;
        kt = new KhoaThi();
        kt.setNgaythi(ngaythi.getValue());
        kt.setTen(tenText.getText());
        khoaThiRepository.save(kt);
        list = FXCollections.observableList(khoaThiRepository.findAll());
        table.setItems(list);
    }
}
