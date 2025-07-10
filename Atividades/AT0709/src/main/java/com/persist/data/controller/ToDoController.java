package com.persist.data.controller;

import com.persist.data.model.ToDo;
import com.persist.data.repositorie.ToDoRepository;
import com.persist.data.service.ToDoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class ToDoController implements Initializable {

    @Autowired
    private ToDoService toDoService;

    @FXML
    private Button adicionarButton;

    @FXML
    private Button deletarButton;

    @FXML
    private Button editarButton;

    @FXML
    private Button cancelarButton;

    @FXML
    private TableView<ToDo> tableView;

    @FXML
    private TableColumn<ToDo, Long> idCol;

    @FXML
    private TableColumn<ToDo, String> descricaoCol;

    @FXML
    private TableColumn<ToDo, Date> dataCriacaoCol;

    @FXML
    private TextArea textField;
    @Autowired
    private ToDoRepository toDoRepository;

    public void desabilitarBotoes(boolean adicionarButton, boolean editarButton, boolean deletarButton, boolean cancelarButton){
        this.adicionarButton.setDisable(adicionarButton);
        this.editarButton.setDisable(editarButton);
        this.deletarButton.setDisable(deletarButton);
        this.cancelarButton.setDisable(cancelarButton);
    }

    public void limparDescricao(){
        this.textField.setText("");
    }

    @FXML
    public void handleToDoSelected(ToDo toDoSelected){
        if(toDoSelected!=null){
            textField.setText(toDoSelected.getDescricao());
            desabilitarBotoes(true, false, false, false);
        }
    }


    @FXML
    public void onAdicionarButton(){
        try {
            if(!textField.getText().isEmpty()) {
                ToDo toDo = new ToDo();
                toDo.setDescricao(textField.getText());
                ToDo toDoTable = toDoService.insert(toDo);
                tableView.getItems().add(toDoTable);
                limparDescricao();
                new Alert(Alert.AlertType.CONFIRMATION, "Tarefa criada").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Campo de inserção está vazio.").show();
            }

        } catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "Erro ao salvar: "  + e.getMessage()).show();
        }
    }

    @FXML
    public void onDeletarButton(){
        ToDo toDoSelected = tableView.getSelectionModel().getSelectedItem();

        try {
            toDoService.delete(toDoSelected);
            tableView.setItems(allToDo());
            limparDescricao();
            desabilitarBotoes(false, true, true, true);

        } catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "Erro ao salvar: "  + e.getMessage()).show();
        }
    }

    @FXML
    public void onEditarButton(){
        ToDo toDoSelected = tableView.getSelectionModel().getSelectedItem();
        toDoSelected.setDescricao(textField.getText());
        ToDo newToDo = new ToDo(toDoSelected.getId(),toDoSelected.getDescricao());

        toDoService.update(toDoSelected.getId(), newToDo);
        limparDescricao();
        desabilitarBotoes(false, true, true, true);
        tableView.setItems(allToDo());
    }

    @FXML
    public void onCancelarButton(){
        tableView.getSelectionModel().clearSelection();
        limparDescricao();
        desabilitarBotoes(false, true, true, true);
    }

    private ObservableList<ToDo> allToDo(){
        ObservableList<ToDo> toDoObservableList = FXCollections.observableArrayList();
        List<ToDo> listFromDB = toDoService.findAll();
        for(ToDo td : listFromDB){
            toDoObservableList.add(td);
        }
        return toDoObservableList;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        descricaoCol.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        dataCriacaoCol.setCellValueFactory(new PropertyValueFactory<>("dataCriacao"));

        // 2. Carrega os itens na tabela chamando seu método de busca
        tableView.setItems(allToDo());
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            handleToDoSelected(newSelection);
        } );
        desabilitarBotoes(false, true, true, true);

    }
}
