package com.persist.data.model;



import jakarta.persistence.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "tb_todo")
public class ToDo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    public Date dataCriacao;

    public ToDo(){}

    public ToDo(Long id, String descricao){
        this.id = id;
        this.descricao = descricao;
        this.dataCriacao =  new Date();

    }
    public ToDo(String descricao){
        this.descricao = descricao;
        this.dataCriacao = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
