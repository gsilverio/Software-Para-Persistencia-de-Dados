package com.atividade.cinco.view;

import com.atividade.cinco.Atividade5;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.net.URL;

/**
 * Interface Gráfica (GUI) do CRUD
 * 
 * @author marceloakira
 * @version 0.01
 */
public class AppView extends Application
{
    private ConfigurableApplicationContext springContext;

    private FXMLLoader loader;
    private URL url;
    private Stage primaryStage;

    @Override
    public void init(){
        springContext = new SpringApplication(Atividade5.class).run();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/app.fxml"));

            fxmlLoader.setControllerFactory(springContext::getBean);

            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            primaryStage.setTitle("App");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void stop() {
        springContext.close();
        System.exit(0);
    }
    
    public void run(String[] args) {
        Application.launch(args);
    }
}