package com.persist.data.repositories;

import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;
import com.j256.ormlite.table.TableUtils;
import com.persist.data.database.Database;
import com.persist.data.entities.Nota;
import com.persist.data.entities.Student;

import java.util.List;
import java.util.ArrayList;

public class StudentRepository
{
    private static Database database;
    private static Dao<Student, Integer> dao;
    private static Dao<Nota, Integer> notaDao;

    private List<Student> loadedStudents;
    private Student loadedStudent;


    public StudentRepository(Database database) {
        StudentRepository.setDatabase(database);
        loadedStudents = new ArrayList<Student>();
    }

    public static void setDatabase(Database database) {
        StudentRepository.database = database;
        try {
            dao = DaoManager.createDao(database.getConnection(), Student.class);
            notaDao = DaoManager.createDao(database.getConnection(), Nota.class);
            TableUtils.createTableIfNotExists(database.getConnection(), Student.class);
        }
        catch(SQLException e) {
            System.out.println(e);
        }
    }

    public Student create(Student student) {
        int nrows = 0;
        try {
            nrows = dao.create(student);
            if ( nrows == 0 )
                throw new SQLException("Error: object not saved");
            this.loadedStudent = student;
            loadedStudents.add(student);
        } catch (SQLException e) {
            System.out.println(e);
        }
        System.out.println("Estudante criado!\n");
        return student;
    }



    public void update(Student student) throws SQLException {
        int nrows = dao.update(student);
        if(nrows == 0){
            throw new SQLException("Falha a atualizar o estudante de id: " + student.getId());
        }
        System.out.println("Estudante atualizado!\n");
    }

    public void delete(int id) throws SQLException {
        Student entity = loadFromId(id);
        deleteOnCascade(entity);
        dao.delete(entity);
        System.out.println("Estudante excluido!\n");
    }

    public void deleteOnCascade(Student student) throws SQLException{
        if(student.getListNotas() != null && !student.getListNotas().isEmpty()){
            System.out.println("Deletando todas as notas do estudante: \n" + student.getFullName());
            notaDao.delete(student.getListNotas());
        }
    }

    public Student loadFromId(int id) {
        try {
            this.loadedStudent = dao.queryForId(id);
            if (this.loadedStudent != null)
                this.loadedStudents.add(this.loadedStudent);
        } catch (SQLException e) {
            System.out.println(e);
        }
        if(loadedStudent!=null)
            System.out.printf("Buscando estudante de id - %d\n", loadedStudent.getId());
        return this.loadedStudent;
    }

    public List<Student> loadAll() {
        try {
            this.loadedStudents =  dao.queryForAll();
            if (this.loadedStudents.size() != 0)
                this.loadedStudent = this.loadedStudents.get(0);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return this.loadedStudents;
    }

    // getters and setters ommited...
}