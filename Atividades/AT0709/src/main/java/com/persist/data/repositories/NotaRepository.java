package com.persist.data.repositories;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.table.TableUtils;
import com.persist.data.database.Database;
import com.persist.data.dto.NotaDTO;
import com.persist.data.entities.Nota;
import jakarta.xml.bind.JAXBException;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotaRepository {
    private static Database database;
    private static Dao<Nota, Integer> dao;
    private List<Nota> loadedNotas;
    private Nota loadedNota;

    NotaSerializer notaSerializer = new NotaSerializer();

    StudentRepository studentRepository;

    public NotaRepository(Database database) throws JAXBException {
        NotaRepository.setDatabase(database);
        studentRepository = new StudentRepository(database);
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

    public Nota createNota(Nota nota){
        int nrows=0;
        try{
            nrows = dao.create(nota);
            if(nrows == 0)
                throw new SQLException("Error: object Not saved\n");
            this.loadedNota = nota;
            nota.setStudent(nota.getStudent());
            loadedNotas.add(nota);
        } catch (SQLException e){
            System.out.println(e);
        }
        return nota;
    }

    public void update(Nota nota) throws SQLException {
        int nrows = dao.update(nota);
        if(nrows == 0){
            throw new SQLException("Falha a atualizar a nota de id: " + nota.getId());
        }
        System.out.println("Nota atualizada!\n");
    }


    public void delete(int id) throws SQLException {
        int nrows = dao.deleteById(id);
        if(nrows == 0 ){
            throw new SQLException("Erro: Nota não encontrada");
        }
        System.out.println("Nota excluida!\n");
    }

    public Nota loadFromId(int id) throws SQLException {
        Nota nota = dao.queryForId(id);
        if(nota !=null) {
            System.out.printf("Buscando nota de id: %s\n", nota.getId());
            System.out.print("Nota: " + nota.getNota());
            System.out.print("\nObs: " + nota.getObs());
            System.out.print("\nData Correção: " + nota.getDataCorrecao());
            System.out.print("\nEstudante: " + nota.getStudent().getFullName() + "\n");

        }
        return nota;
    }

    public List<Nota> loadAll() {
        try {
            this.loadedNotas =  dao.queryForAll();
            if (this.loadedNotas.size() != 0)
                this.loadedNota = this.loadedNotas.get(0);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return this.loadedNotas;
    }

    public boolean dumpFile(String datatype, File file) throws IOException, JAXBException {
        if(datatype.equalsIgnoreCase("JSON")){
            FileWriter fileWriter = new FileWriter(file);

            List<Nota> listNotas = loadAll();
            for(Nota n : listNotas){
                NotaDTO dto = new NotaDTO(n.getId(), n.getNota(), n.getObs(), n.getDataCorrecao(), n.getStudent().getId());
                String json = notaSerializer.toJson(dto);
                fileWriter.write(json);
            }
            fileWriter.close();
            return true;
        } else if (datatype.equalsIgnoreCase("XML")) {
            FileWriter fileWriter = new FileWriter(file);
            List<Nota> listNotas = loadAll();
            for(Nota n : listNotas){
                NotaDTO dto = new NotaDTO(n.getId(), n.getNota(), n.getObs(), n.getDataCorrecao(), n.getStudent().getId());
                String xml = notaSerializer.toXml(dto);
                fileWriter.write(xml);
            }
            fileWriter.close();
            return true;
        }
        return false;
    }

    public String dumpData(String dataType) throws IOException, JAXBException {
        if(dataType.equalsIgnoreCase("JSON")){
            List<Nota> listNotas = loadAll();
            for(Nota n : listNotas){
                NotaDTO dto = new NotaDTO(n.getId(), n.getNota(), n.getObs(), n.getDataCorrecao(), n.getStudent().getId());
                String eachData = notaSerializer.toJson(dto);
                System.out.println(eachData);
            }

        } else if(dataType.equalsIgnoreCase("XML")){
            List<Nota> listNotas = loadAll();
            for(Nota n : listNotas){
                NotaDTO dto = new NotaDTO(n.getId(), n.getNota(), n.getObs(), n.getDataCorrecao(), n.getStudent().getId());
                String eachData = notaSerializer.toXml(dto);
                System.out.println(eachData);
            }
            return "";
        } else {
            throw  new IOException("Data type não suportado.");
        }
        return "";
    }


    public Nota createFromJson(String json) throws IOException {
        NotaDTO notaDTOJson = notaSerializer.createFromJson(json);

        Nota newNota = new Nota();
        newNota.setNota(notaDTOJson.getNota());
        newNota.setObs(notaDTOJson.getObs());
        newNota.setDataCorrecao(notaDTOJson.getDataCorrecao());
        newNota.setStudent(studentRepository.loadFromId(notaDTOJson.getStudent_id()));

        createNota(newNota);

        return newNota;
    }

    public Nota createFromXml(String xml) throws IOException, JAXBException {
        NotaDTO notaDTOJson = notaSerializer.createFromXml(xml);

        Nota newNota = new Nota();
        newNota.setNota(notaDTOJson.getNota());
        newNota.setObs(notaDTOJson.getObs());
        newNota.setDataCorrecao(notaDTOJson.getDataCorrecao());
        newNota.setStudent(studentRepository.loadFromId(notaDTOJson.getStudent_id()));

        createNota(newNota);

        return newNota;
    }

    public int importData(String dataType, String data){
        if(dataType.equalsIgnoreCase("JSON")){

            List<NotaDTO> listNotasDTO = notaSerializer.toJsonList(data, NotaDTO.class);

            for(NotaDTO n : listNotasDTO){
                Nota entity = new Nota();
                entity.setNota(n.getNota());
                entity.setObs(n.getObs());
                entity.setDataCorrecao(n.getDataCorrecao());
                entity.setStudent(studentRepository.loadFromId(n.getStudent_id()));

                createNota(entity);

            }
            return listNotasDTO.size();
        } else if(dataType.equalsIgnoreCase("xml")){

            List<NotaDTO> listNotasDTO = notaSerializer.toXmlToList(data);

            for(NotaDTO n : listNotasDTO){
                Nota entity = new Nota();
                entity.setNota(n.getNota());
                entity.setObs(n.getObs());
                entity.setDataCorrecao(n.getDataCorrecao());
                entity.setStudent(studentRepository.loadFromId(n.getStudent_id()));

                createNota(entity);

            }
            return listNotasDTO.size();
        }

        return 0;
    }

}
