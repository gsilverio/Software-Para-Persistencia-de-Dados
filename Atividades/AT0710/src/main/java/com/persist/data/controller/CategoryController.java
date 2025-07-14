package com.persist.data.controller;

import com.persist.data.model.Category;
import com.persist.data.service.AbstractCrudService;

import com.persist.data.service.CategoryService;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryController extends AbstractController<Category, Long> {

    @Autowired
    private CategoryService categoryService;

    @FXML private TableColumn<Category, Long> idCol;
    @FXML private TableColumn<Category, String> nomeCol;

    @Override
    protected AbstractCrudService<Category, Long> getService() {
        return categoryService;
    }

    @Override
    protected void configureTableColumns() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomeCol.setCellValueFactory(new PropertyValueFactory<>("nomeCategoria"));
    }

    @Override
    protected Category createNewEntity(String text) {
        return new Category(text);
    }


    @Override
    protected String getEntityDescription(Category entity) {
        return entity.getNomeCategoria();
    }

    // O método updateEntity foi removido, pois a lógica agora está na classe abstrata!
    // Isso resolve o erro do @Override.
}