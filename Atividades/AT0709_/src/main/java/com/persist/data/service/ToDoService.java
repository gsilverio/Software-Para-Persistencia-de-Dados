package com.persist.data.service;

import com.persist.data.model.ToDo;
import com.persist.data.repositorie.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoService extends AbstractCrudService<ToDo, Long> {

    @Autowired
    private ToDoRepository toDoRepository;

    protected JpaRepository<ToDo, Long> getRepository(){
        return  toDoRepository;
    }

    @Override
    protected void updateEntityData(ToDo existingEntity, ToDo newData) {
        existingEntity.setDescricao(newData.getDescricao());
        existingEntity.setDataCriacao(newData.getDataCriacao());
    }

}
