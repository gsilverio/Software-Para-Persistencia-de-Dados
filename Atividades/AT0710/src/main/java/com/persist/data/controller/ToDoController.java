package com.persist.data.controller;

import com.persist.data.interfaces.Identifiable;
import com.persist.data.model.Category;
import com.persist.data.model.ToDo;
import com.persist.data.repositorie.ToDoRepository;
import com.persist.data.service.AbstractCrudService;
import com.persist.data.service.CategoryService;
import com.persist.data.service.ToDoService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
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

    @Autowired
    private CategoryService categoryService;

    @FXML
    private TableColumn<ToDo, Long> idCol;

    @FXML
    private TableColumn<ToDo, String> descricaoCol;

    @FXML
    private TableColumn<ToDo, Date> dataCriacaoCol;

    @FXML
    private ComboBox<Category> comboBoxButton;

    @FXML
    private TableColumn<ToDo, String> categoriaCol;


    @Override
    protected AbstractCrudService<ToDo, Long> getService() {
        return toDoService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle); // Mantém a inicialização da classe pai
        loadCategorias();
    }

    @FXML
    private void onComboBoxShowing(Event event) {
        System.out.println("ComboBox está sendo aberto. Recarregando categorias...");
        loadCategorias();
    }


    @Override
    protected  void configureTableColumns(){
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        descricaoCol.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        dataCriacaoCol.setCellValueFactory(new PropertyValueFactory<>("dataCriacao"));

        categoriaCol.setCellValueFactory(cellData -> {
            Category category = cellData.getValue().getCategoria();
            String description = (category != null) ? category.getNomeCategoria() : "";
            return new SimpleStringProperty(description);
        });
    }

    @Override
    protected ToDo createNewEntity(String text){
        ToDo toDo = new ToDo();
        toDo.setDescricao(text);
        toDo.setDataCriacao(new Date());
        Category selectedCategory = comboBoxButton.getValue();
        if(selectedCategory == null){
            new Alert(Alert.AlertType.ERROR, "Erro de validação");
            return null;
        }
        toDo.setCategoria(selectedCategory);
        return toDo;
    }



    private void loadCategorias(){
        List<Category> categories = categoryService.findAll();

        comboBoxButton.setItems(FXCollections.observableArrayList(categories));
    }


    @Override
    protected String getEntityDescription(ToDo entity) {
        return entity.getDescricao();
    }

}
