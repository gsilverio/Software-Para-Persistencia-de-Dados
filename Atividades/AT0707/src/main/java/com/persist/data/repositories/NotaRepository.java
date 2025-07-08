package com.persist.data.repositories;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.table.TableUtils;
import com.persist.data.database.Database;
import com.persist.data.entities.Nota;
import com.persist.data.entities.Student;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotaRepository {
    private static Database database;
    private static Dao<Nota, Integer> dao;
    private List<Nota> loadedNotas;

    private Nota loadedNota;

    public NotaRepository(Database database) {
        NotaRepository.setDatabase(database);
        loadedNotas = new ArrayList<Nota>();
    }

    public static void setDatabase(Database database) {
        NotaRepository.database = database;
        try {
            dao = DaoManager.createDao(database.getConnection(), Nota.class);
            TableUtils.createTableIfNotExists(database.getConnection(), Nota.class);
        }
        catch(SQLException e) {
            System.out.println(e);
        }
    }

    public Nota createNota(Nota nota, Student student){
        int nrows=0;
        try{
            nrows = dao.create(nota);
            if(nrows == 0)
                throw new SQLException("Error: object Not saved");
            this.loadedNota = nota;
            nota.setStudent(student);
            loadedNotas.add(nota);
        } catch (SQLException e){
            System.out.println(e);
        }
        return nota;
    }
}
