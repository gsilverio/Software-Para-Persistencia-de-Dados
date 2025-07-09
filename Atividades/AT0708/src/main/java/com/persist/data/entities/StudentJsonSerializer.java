package com.persist.data.entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class StudentJsonSerializer
{
    private Gson gson;

    public StudentJsonSerializer() {
        // Configurando para formatar datas e pretty printing
        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .setPrettyPrinting()
                .create();
    }

    public String toJson(Student student) {
        return gson.toJson(student);
    }

    public Student fromJson(String json) {
        return gson.fromJson(json, Student.class);
    }
}
