package com.atividade.cinco.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component; // Importe esta anotação

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component // 1. Torna esta classe um Bean gerenciado pelo Spring
public class AppController implements Initializable {
    @FXML private Tab tabEstudante;
    @FXML private Tab tabDisciplina;
    @FXML private Tab tabTurma;
    @FXML private Tab tabMatricula;

    // 2. Injeta o contexto do Spring diretamente no controlador
    @Autowired
    private ConfigurableApplicationContext springContext;

    // 3. REMOVA o construtor manual e o método main. O Spring cuidará disso.
    // public AppController() { ... }
    // public static void main(String[] args) { ... }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Carrega cada FXML usando um método auxiliar que conhece o Spring
        tabEstudante.setContent(loadTabContent("/view/estudante.fxml"));
        tabDisciplina.setContent(loadTabContent("/view/disciplina.fxml"));
        tabTurma.setContent(loadTabContent("/view/turma.fxml"));
        tabMatricula.setContent(loadTabContent("/view/matricula.fxml"));
    }

    /**
     * ✅ MÉTODO CHAVE: Carrega um FXML usando um FXMLLoader configurado com o
     * contexto do Spring, garantindo que os controladores filhos tenham suas
     * dependências injetadas corretamente.
     */
    private Parent loadTabContent(String fxmlPath) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            // Informa ao novo loader como obter beans (controladores) do Spring
            fxmlLoader.setControllerFactory(springContext::getBean);
            return fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            // Retorna um painel de erro ou lança uma exceção
            return new Label("Erro ao carregar a aba: " + fxmlPath);
        }
    }
}