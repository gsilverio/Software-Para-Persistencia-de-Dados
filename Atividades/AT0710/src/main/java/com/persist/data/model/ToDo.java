package com.persist.data.model;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.persist.data.interfaces.Identifiable;
import jakarta.persistence.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "tb_todo")
public class ToDo implements Identifiable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;



    private Date dataCriacao;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id")
    @JsonIgnore
    private Category categoria;


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

    @Override
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

    public Category getCategoria() {
        return categoria;
    }

    public void setCategoria(Category categoria) {
        this.categoria = categoria;
    }
}
