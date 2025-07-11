package com.persist.data.controller;

import com.persist.data.interfaces.Identifiable;
import com.persist.data.model.ToDo;
import com.persist.data.service.AbstractCrudService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.ResourceBundle;
import java.net.URL;

public abstract class AbstractController<T extends Identifiable<ID>, ID> implements Initializable {



    @FXML
    private Button adicionarButton;

    @FXML
    private Button deletarButton;

    @FXML
    private Button editarButton;

    @FXML
    private Button cancelarButton;

    @FXML
    private TextArea textField;

    @FXML
    private TableView<T> tableView;


    protected  abstract AbstractCrudService<T, ID> getService();

    protected abstract void configureTableColumns();

    protected abstract T createNewEntity(String text);


    protected abstract String getEntityDescription(T entity);


    @Override
    public void initialize(URL location, ResourceBundle resourceBundle){
        configureTableColumns();
        loadData();

        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> handleSelectionChange(newSelection));

        resetUIState();
    }

    protected void disableButton(boolean adicionarButton, boolean deletarButton, boolean editarButton, boolean cancelarButton){
        this.adicionarButton.setDisable(adicionarButton);
        this.deletarButton.setDisable(deletarButton);
        this.editarButton.setDisable(editarButton);
        this.cancelarButton.setDisable(cancelarButton);

    }

    protected void handleSelectionChange(T selectItem){
        boolean isItemSelected = selectItem != null;
        disableButton(false, true, true, true);

        textField.setText(isItemSelected ? getEntityDescription(selectItem) : "");

    }

    protected  void loadData(){
        List<T> items = getService().findAll();
        tableView.setItems(FXCollections.observableArrayList(items));
    }

    @FXML
    public void onAdicionarButton(){

        String text = textField.getText().trim();
        if(text.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Campo de inserção está vazio.").show();
        } else {
            T entity = createNewEntity(text);
            getService().save(entity);
            loadData();
            textField.clear();
            new Alert(Alert.AlertType.INFORMATION, "Item criado com sucesso!");
        }

    }

    @FXML
    protected void onEditarButton(){
        T selectedItem = tableView.getSelectionModel().getSelectedItem();
        String newText = textField.getText().trim();

        if (selectedItem == null || newText.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Selecione um item e preencha a descrição.").show();
            return;
        }

        // A lógica de update agora está centralizada aqui.
        // Usamos o método update do nosso serviço genérico.
        // A classe filha não precisa mais se preocupar com isso.
        getService().update(selectedItem.getId(), createNewEntity(newText));

        loadData();
        new Alert(Alert.AlertType.INFORMATION,"Item atualizado com sucesso!").show();
    }

    @FXML
    protected void onDeletarButton() {
        T selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) return;

        getService().deleteById(selectedItem.getId());
        loadData();
        new Alert(Alert.AlertType.INFORMATION, "Item deletado com sucesso.");
    }

    @FXML
    protected void onCancelarButton() {
        tableView.getSelectionModel().clearSelection();
    }

    private void resetUIState() {
        tableView.getSelectionModel().clearSelection();
        textField.clear();
        adicionarButton.setDisable(false);
        editarButton.setDisable(true);
        deletarButton.setDisable(true);
        cancelarButton.setDisable(true);
    }

}
