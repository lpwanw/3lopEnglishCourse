package com.lpwanw.appui;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

public class Hello extends Application {

    private ConfigurableApplicationContext applicaitonContext;

    @Override
    public void start(Stage stage) throws Exception {
        applicaitonContext.publishEvent(new StageReadyEvent(stage));
    }

    @Override
    public void init(){
        applicaitonContext = new SpringApplicationBuilder(AppUiApplication.class).run();
    }

    @Override
    public void stop(){
        applicaitonContext.close();
        Platform.exit();
    }

    class StageReadyEvent extends ApplicationEvent {
        public StageReadyEvent(Stage stage) {
            super(stage);
        }

        public Stage getStage() {
            return (Stage) getSource();
        }
    }
}
