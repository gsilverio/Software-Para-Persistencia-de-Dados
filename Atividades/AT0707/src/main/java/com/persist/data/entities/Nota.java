package com.persist.data.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.math.BigDecimal;

@DatabaseTable(tableName = "tb_nota")
public class Nota {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private double nota;
    @DatabaseField
    private String obs;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "student_id")
    private Student student;

    public Nota(){}


    public Nota(double nota, String obs) {
        this.nota = nota;
        this.obs = obs;

    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
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
}
