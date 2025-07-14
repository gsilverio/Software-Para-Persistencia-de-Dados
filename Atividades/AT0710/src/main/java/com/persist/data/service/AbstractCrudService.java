package com.persist.data.service;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public abstract class AbstractCrudService<T, ID> {

    protected abstract JpaRepository<T, ID> getRepository();

    public List<T> findAll() { return getRepository().findAll(); }
    public T save(T entity) { return getRepository().save(entity); }
    public void deleteById(ID id) { getRepository().deleteById(id); }

    // Método de update concreto que usa um método abstrato para os detalhes
    public T update(ID id, T newData) {
        T dbEntity = getRepository().findById(id).orElseThrow(() -> new RuntimeException("Entidade não encontrada"));
        updateEntityData(dbEntity, newData);
        return getRepository().save(dbEntity);
    }

    // Este é o único método que o serviço filho precisa implementar para o update
    protected abstract void updateEntityData(T existingEntity, T newDataFromForm);
}