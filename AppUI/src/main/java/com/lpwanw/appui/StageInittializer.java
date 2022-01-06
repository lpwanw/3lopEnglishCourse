package com.lpwanw.appui;

import com.lpwanw.appui.Hello.StageReadyEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StageInittializer implements ApplicationListener<StageReadyEvent> {
    @Value("classpath:/fxml/home.fxml")
    private Resource homeResouce;
    private final String applicationTitle;
    public final ApplicationContext applicaitonContext;

    public StageInittializer(@Value("${spring.application.ui.title}") String applicationTitle, ApplicationContext applicaitonContext){
        this.applicationTitle = applicationTitle;
        this.applicaitonContext = applicaitonContext;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(homeResouce.getURL());
            fxmlLoader.setControllerFactory(applicaitonContext::getBean);
            Parent parent = fxmlLoader.load();

            Stage stage = event.getStage();
            stage.setTitle(applicationTitle);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException();
        }

    }
}
