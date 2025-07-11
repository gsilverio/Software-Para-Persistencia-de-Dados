package com.persist.data.controller;

import com.persist.data.interfaces.Identifiable;
import com.persist.data.model.ToDo;
import com.persist.data.repositorie.ToDoRepository;
import com.persist.data.service.AbstractCrudService;
import com.persist.data.service.ToDoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class ToDoController extends AbstractController<ToDo, Long> {

    @Autowired
    private ToDoService toDoService;

    @FXML
    private TableColumn<ToDo, Long> idCol;

    @FXML
    private TableColumn<ToDo, String> descricaoCol;

    @FXML
    private TableColumn<ToDo, Date> dataCriacaoCol;

    @Override
    protected AbstractCrudService<ToDo, Long> getService() {
        return toDoService;
    }
    @Override
    protected  void configureTableColumns(){
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        descricaoCol.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        dataCriacaoCol.setCellValueFactory(new PropertyValueFactory<>("dataCriacao"));
    }

    @Override
    protected ToDo createNewEntity(String text){
        ToDo toDo = new ToDo();
        toDo.setDescricao(text);
        toDo.setDataCriacao(new Date());
        return toDo;
    }


    @Override
    protected String getEntityDescription(ToDo entity) {
        return entity.getDescricao();
    }

}
