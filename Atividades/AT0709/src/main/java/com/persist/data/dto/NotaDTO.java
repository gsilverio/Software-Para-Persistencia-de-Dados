package com.persist.data.dto;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.persist.data.entities.Student;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.math.BigDecimal;
import java.util.Date;
@XmlRootElement(name = "nota")
@XmlAccessorType(XmlAccessType.FIELD)
public class NotaDTO {

    private int id;

    private BigDecimal nota;

    private String obs;

    private int student_id;

    private Date dataCorrecao;

    public NotaDTO(){}

    public NotaDTO(int id, BigDecimal nota, String obs, Date dataCorrecao, int student_id) {
        this.id = id;
        this.nota = nota;
        this.obs = obs;
        this.dataCorrecao = dataCorrecao;
        this.student_id = student_id;
    }

    public NotaDTO(BigDecimal nota, String obs, Date dataCorrecao, int student_id) {
        this.nota = nota;
        this.obs = obs;
        this.dataCorrecao = dataCorrecao;
        this.student_id = student_id;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getNota() {
        return nota;
    }

    public void setNota(BigDecimal nota) {
        this.nota = nota;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public Date getDataCorrecao() {
        return dataCorrecao;
    }

    public void setDataCorrecao(Date dataCorrecao) {
        this.dataCorrecao = dataCorrecao;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }
}
