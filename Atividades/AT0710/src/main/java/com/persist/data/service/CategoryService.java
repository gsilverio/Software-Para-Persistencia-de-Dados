package com.persist.data.service;

import com.persist.data.model.Category;
import com.persist.data.repositorie.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends AbstractCrudService<Category, Long> {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    protected JpaRepository<Category, Long> getRepository() {
        return categoryRepository;
    }

    @Override
    protected void updateEntityData(Category existingEntity, Category newDataFromForm) {
        existingEntity.setNomeCategoria(newDataFromForm.getNomeCategoria());
    }
}