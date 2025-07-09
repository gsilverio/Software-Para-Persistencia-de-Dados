package com.persist.data.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.persist.data.database.Database;
import com.persist.data.dto.NotaDTO;
import com.persist.data.entities.Nota;
import com.persist.data.entities.NotasWrapper;
import com.persist.data.repositories.NotaRepository;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotaSerializer
{
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

    public String createFromJsonList(List<NotaDTO> dtoList){
        return gson.toJson(dtoList);
    }

    public String createFromXmlList(List<NotaDTO>dtoList){
        if (dtoList == null || dtoList.isEmpty()) {
            return "<notas/>"; // Retorna um XML vazio
        }

        try {
            // O contexto precisa conhecer AMBAS as classes: o wrapper e o item.
            JAXBContext context = JAXBContext.newInstance(NotasWrapper.class, NotaDTO.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); // Para formatar o XML

            // 1. Cria o objeto wrapper
            NotasWrapper wrapper = new NotasWrapper();
            // 2. Coloca a lista dentro do wrapper
            wrapper.setNotas(dtoList);

            StringWriter sw = new StringWriter();
            // 3. Serializa o objeto WRAPPER
            marshaller.marshal(wrapper, sw);
            return sw.toString();

        } catch (JAXBException e) {
            System.err.println("Erro ao serializar lista para XML: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public <T> List<T> toJsonList(String jsonString, Class<T> itemClass){
        if (jsonString == null || jsonString.trim().isEmpty()) {
            return Collections.emptyList();
        }
        try {
            // O truque está aqui! Construímos o tipo da lista dinamicamente.
            Type listType = TypeToken.getParameterized(List.class, itemClass).getType();
            return gson.fromJson(jsonString, listType);
        } catch (JsonSyntaxException e) {
            System.err.println("Erro de sintaxe no JSON: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<NotaDTO> toXmlToList(String xmlString) {
        if (xmlString == null || xmlString.trim().isEmpty()) {
            return Collections.emptyList();
        }

        try {
            JAXBContext context = JAXBContext.newInstance(NotasWrapper.class, NotaDTO.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            StringReader sr = new StringReader(xmlString);

            // 1. Desserializa o XML para o objeto WRAPPER
            NotasWrapper wrapper = (NotasWrapper) unmarshaller.unmarshal(sr);

            // 2. Extrai a lista de dentro do wrapper
            return wrapper.getNotas();

        } catch (JAXBException e) {
            System.err.println("Erro ao desserializar XML para lista: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public NotaDTO createFromXml(String xml) throws JAXBException {
        Unmarshaller unmarshaller = context.createUnmarshaller();
        StringReader sr = new StringReader(xml);
        return (NotaDTO) unmarshaller.unmarshal(sr);
    }

}
