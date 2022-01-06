package com.lpwanw.appui.controller;

import com.lpwanw.appui.Entity.Student;
import com.lpwanw.appui.StageInittializer;
import com.lpwanw.appui.reponsitory.KhoaThiRepository;
import com.lpwanw.appui.reponsitory.StudentRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class HomeController implements Initializable {
    public Button SizeButton;
    public SplitPane HomePane;
    public BorderPane main;
    private Parent khoaThi;
    private Parent phongThi;
    private Parent thiSinh;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    StageInittializer stageInittializer;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        khoaThi = loadFXML(getClass().getResource("/fxml/khoathi.fxml")).getRoot();
        phongThi = loadFXML(getClass().getResource("/fxml/phongthi.fxml")).getRoot();
        thiSinh = loadFXML(getClass().getResource("/fxml/thisinh.fxml")).getRoot();
    }
    public FXMLLoader loadFXML(URL url){
        FXMLLoader fxmlLoader = new FXMLLoader ();
        fxmlLoader.setLocation (url);
        try {
            fxmlLoader.setControllerFactory(aClass-> stageInittializer.applicaitonContext.getBean(aClass));
            fxmlLoader.load();
            return fxmlLoader;
        } catch (IOException e) {
            e.printStackTrace(System.err);
            throw new IllegalStateException(e);
        }
    }

    public void onKhoaThiButton(ActionEvent actionEvent) {
        main.setCenter(khoaThi);
    }

    public void onHomeButton(ActionEvent actionEvent) {
        main.setCenter(HomePane);
    }

    public void onPhongThiAction(ActionEvent actionEvent) {
        main.setCenter(phongThi);
    }

    public void onThiSinhAction(ActionEvent actionEvent) {
        main.setCenter(thiSinh);
    }
}
