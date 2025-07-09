package com.persist.data.entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.persist.data.database.Database;
import com.persist.data.dto.NotaDTO;
import com.persist.data.repositories.NotaRepository;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class NotaSerializer
{
    Database database = new Database("teste.db");
    NotaRepository notaRepository = new NotaRepository(database);
    private Gson gson;

    private JAXBContext context;

    public NotaSerializer() throws JAXBException {
        // Configurando para formatar datas e pretty printing
        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .setPrettyPrinting()
                .create();

        context = JAXBContext.newInstance(NotaDTO.class);
    }

    public String toJson(Nota nota) {
        return gson.toJson(nota);
    }

    public String toJson(NotaDTO nota) {
        return gson.toJson(nota);
    }
    public String toXml(NotaDTO notaDTO) throws JAXBException {
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        StringWriter sw = new StringWriter();
        marshaller.marshal(notaDTO, sw);
        return sw.toString();
    }

    public NotaDTO createFromJson(String json) {
        return gson.fromJson(json, NotaDTO.class);
    }


    public String dumpData(String dataType) throws IOException, JAXBException {
        if(dataType.toUpperCase().equals("JSON")){
            List<Nota> listNotas = notaRepository.loadAll();
            List<NotaDTO> listNotasDTO = new ArrayList<>();

            for(Nota n : listNotas){
                NotaDTO dto = new NotaDTO(n.getId(), n.getNota(), n.getObs(), n.getDataCorrecao(), n.getStudent().getId());
                String eachData = toJson(dto);
                System.out.println(eachData);
            }

        } else if(dataType.toUpperCase().equals("XML")){
            List<Nota> listNotas = notaRepository.loadAll();
            List<NotaDTO> listNotasDTO = new ArrayList<>();

            for(Nota n : listNotas){
                NotaDTO dto = new NotaDTO(n.getId(), n.getNota(), n.getObs(), n.getDataCorrecao(), n.getStudent().getId());
                String eachData = toXml(dto);
                System.out.println(eachData);
            }
            return "";
        }
        return ("Data type não suportado.");
    }

    public boolean dumpFile(String datatype, File file) throws IOException, JAXBException {
        if(datatype.toUpperCase().equals("JSON")){
            FileWriter fileWriter = new FileWriter(file);

            List<Nota> listNotas = notaRepository.loadAll();
            List<NotaDTO> listNotasDTO = new ArrayList<>();

            for(Nota n : listNotas){
                NotaDTO dto = new NotaDTO(n.getId(), n.getNota(), n.getObs(), n.getDataCorrecao(), n.getStudent().getId());
                String json = toJson(dto);
                fileWriter.write(json);
            }
            fileWriter.close();
            return true;
        } else if (datatype.toUpperCase().equals("XML")) {
            FileWriter fileWriter = new FileWriter(file);
            List<Nota> listNotas = notaRepository.loadAll();
            List<NotaDTO> listNotasDTO = new ArrayList<>();

            for(Nota n : listNotas){
                NotaDTO dto = new NotaDTO(n.getId(), n.getNota(), n.getObs(), n.getDataCorrecao(), n.getStudent().getId());
                String xml = toXml(dto);
                fileWriter.write(xml);
            }
            fileWriter.close();
            return true;
        }
        return false;
    }
}
