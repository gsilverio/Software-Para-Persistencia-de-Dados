package com.persist.data.entities;

import com.persist.data.dto.NotaDTO;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "notas") // Nome do elemento raiz para a lista
@XmlAccessorType(XmlAccessType.FIELD)
public class NotasWrapper {

    @XmlElement(name = "nota") // Nome da tag para cada item na lista
    private List<NotaDTO> notas;

    public List<NotaDTO> getNotas() {
        return notas;
    }

    public void setNotas(List<NotaDTO> notas) {
        this.notas = notas;
    }
}
