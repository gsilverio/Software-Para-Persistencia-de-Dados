package com.persist.data.model;

import com.persist.data.interfaces.Identifiable; // Importe a interface
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_category")
public class Category implements Identifiable<Long> { // Adicione o "implements" aqui

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeCategoria;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ToDo> toDoList = new ArrayList<>();

    public Category() {}

    public Category(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void addToDo(ToDo toDo){
        toDoList.add(toDo);
    }

    public void removeToDo(ToDo toDo){
        toDoList.remove(toDo);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }


    @Override
    public String toString(){
        return this.nomeCategoria;
    }
}