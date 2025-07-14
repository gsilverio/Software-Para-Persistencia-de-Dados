package com.atividade.cinco.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

public abstract class AbstractCrudService<T, ID> {


    private List<T> loadedEntities;
    private T loadedEntity;
    private Class<T> entityClass;

    protected abstract JpaRepository<T, ID> getRepository();

    public T create(T entity) {
        getRepository().save(entity);
        this.loadedEntity = entity;
        loadedEntities.add(entity);
        return entity;
    }

    public void delete(T entity) {
        getRepository().delete(entity);
    }

    public T loadFromId(ID id) {
        this.loadedEntity = getRepository().getReferenceById(id);
        if (this.loadedEntity != null)
            this.loadedEntities.add(this.loadedEntity);
        return this.loadedEntity;
    }

    public List<T> loadAll() {
        this.loadedEntities = getRepository().findAll();
        if (!this.loadedEntities.isEmpty())
            this.loadedEntity = this.loadedEntities.getFirst();
        return this.loadedEntities;
    }


}
