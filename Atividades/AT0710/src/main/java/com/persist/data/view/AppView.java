package com.persist.data.view;

import com.persist.data.Atividade3;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

public class AppView extends Application {

    private ConfigurableApplicationContext springContext;

    @Override
    public void init(){
        springContext = new SpringApplication(Atividade3.class).run();
    }

    @Override
    public void start(Stage stage) throws IOException {
        try{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/templateInicial.fxml"));

        fxmlLoader.setControllerFactory(springContext::getBean);

        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setTitle("TO-DO List App");
        stage.setScene(scene);
        stage.show();}catch (Exception e) {
            System.err.println("Erro ao carregar o FXML");
            e.printStackTrace();
        }
    }

    @Override
    public void stop(){
        springContext.close();
        Platform.exit();
    }
}
