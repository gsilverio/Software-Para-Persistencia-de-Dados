package com.atividade.odm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "turma")
public class Turma {
    @Id
    private String id;
    private String nomeTurma;
    private String codigoTurma;
    private Integer quantidadeAlunos;

    public Turma(){}

    public Turma(String nomeTurma, String codigoTurma, Integer quantidadeAlunos) {
        this.nomeTurma = nomeTurma;
        this.codigoTurma = codigoTurma;
        this.quantidadeAlunos = quantidadeAlunos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomeTurma() {
        return nomeTurma;
    }

    public void setNomeTurma(String nomeTurma) {
        this.nomeTurma = nomeTurma;
    }

    public String getCodigoTurma() {
        return codigoTurma;
    }

    public void setCodigoTurma(String codigoTurma) {
        this.codigoTurma = codigoTurma;
    }

    public Integer getQuantidadeAlunos() {
        return quantidadeAlunos;
    }

    public void setQuantidadeAlunos(Integer quantidadeAlunos) {
        this.quantidadeAlunos = quantidadeAlunos;
    }
}
