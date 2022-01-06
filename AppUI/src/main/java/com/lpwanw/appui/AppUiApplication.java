package com.lpwanw.appui;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppUiApplication {

    public static void main(String[] args) {
        Application.launch(Hello.class, args);
    }
}
