package com.persist.data.service;

import com.persist.data.model.ToDo;
import com.persist.data.repositorie.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoService {

    @Autowired
    private ToDoRepository toDoRepository;

    public List<ToDo> findAll(){
        List<ToDo> toDoList = toDoRepository.findAll();
        return toDoList;
    }
    public ToDo insert(ToDo dto){
        ToDo entity = new ToDo(dto.getDescricao());
        toDoRepository.save(entity);
        return entity;
    }

    public ToDo update(Long id, ToDo dto){
        ToDo toDo = toDoRepository.findById(id).orElseThrow(() -> new RuntimeException("Id nao encontrado."));
        toDo.setDescricao(dto.getDescricao());
        toDo.setDataCriacao(dto.getDataCriacao());
        return toDoRepository.save(toDo);
    }

    public void delete(ToDo toDo){
        toDoRepository.delete(toDo);
    }


}
