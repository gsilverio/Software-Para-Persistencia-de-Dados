package com.persist.data.entities;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.StringWriter;
import java.io.StringReader;

public class StudentXmlSerializer
{
    private JAXBContext context;

    public StudentXmlSerializer() throws JAXBException {
        context = JAXBContext.newInstance(Student.class);
    }

    public String toXml(Student student) throws JAXBException {
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        StringWriter sw = new StringWriter();
        marshaller.marshal(student, sw);
        return sw.toString();
    }

    public Student fromXml(String xml) throws JAXBException {
        Unmarshaller unmarshaller = context.createUnmarshaller();
        StringReader sr = new StringReader(xml);
        return (Student) unmarshaller.unmarshal(sr);
    }
}