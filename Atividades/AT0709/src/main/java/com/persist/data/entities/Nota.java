package com.persist.data.entities;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.math.BigDecimal;
import java.util.Date;

@DatabaseTable(tableName = "tb_nota")
public class Nota {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnDefinition = "DECIMAL(19, 4) NOT NULL")
    private BigDecimal nota;
    @DatabaseField
    private String obs;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "student_id")
    private transient Student student;

    @DatabaseField(dataType= DataType.DATE)
    private Date dataCorrecao;

    public Nota(){}

    public Nota(BigDecimal nota, String obs, Date dataCorrecao) {
        this.nota = nota;
        this.obs = obs;
        this.dataCorrecao = dataCorrecao;

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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Date getDataCorrecao() {
        return dataCorrecao;
    }

    public void setDataCorrecao(Date dataCorrecao) {
        this.dataCorrecao = dataCorrecao;
    }
}
