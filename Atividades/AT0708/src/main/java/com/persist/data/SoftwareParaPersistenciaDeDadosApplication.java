package com.persist.data;

import com.google.gson.Gson;
import com.persist.data.database.Database;
import com.persist.data.dto.NotaDTO;
import com.persist.data.entities.*;
import com.persist.data.repositories.NotaRepository;
import com.persist.data.repositories.StudentRepository;
import com.persist.data.serializer.NotaSerializer;
import jakarta.xml.bind.JAXBException;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@SpringBootApplication
public class SoftwareParaPersistenciaDeDadosApplication {

	public static void main(String[] args) throws SQLException, JAXBException {

		Database database = null;

		try {
			database = new Database("teste.db");
			StudentRepository studentRepository = new StudentRepository(database);
			NotaRepository notaRepository = new NotaRepository(database);
			NotaSerializer notaSerializer = new NotaSerializer();

			List<NotaDTO> list = new ArrayList<>();
			NotaDTO notaDTO3 = new NotaDTO(new BigDecimal("25.00"), "Notão!!", new Date(), 3);
			NotaDTO notaDTO4 = new NotaDTO(new BigDecimal("30.00"), "Nuhh!!", new Date(), 4);
			list.add(notaDTO3);
			list.add(notaDTO4);

			List<NotaDTO> list2 = new ArrayList<>();
			NotaDTO notaDTO5 = new NotaDTO(new BigDecimal("2.00"), "Estude mais!!", new Date(), 3);
			list2.add(notaDTO5);

			String listNotaDto = notaSerializer.createFromJsonList(list2);
			String listNotaXml =  notaSerializer.createFromXmlList(list);

			System.out.println("json's add: " + notaRepository.importData("json", listNotaDto));
			System.out.println("xml's add: " + notaRepository.importData("xml", listNotaXml));

			/*
			notaRepository.dumpFile("JSON", new File("notas-json.json"));
			notaRepository.dumpFile("XML", new File("notas-xml.xml"));
			notaRepository.dumpData("XML");

			NotaDTO notaDTO = new NotaDTO(new BigDecimal("15.00"), "Muito boa a nota", new Date(), 1);
			String newNotaJson = notaSerializer.toJson(notaDTO);
			notaRepository.createFromJson(newNotaJson);

			NotaDTO notaDTO2 = new NotaDTO(new BigDecimal("20.00"), "Parabens!!", new Date(), 3);
			String newNotaXml = notaSerializer.toXml(notaDTO2);
			System.out.println("Xml a ser inserido: \n" + newNotaXml);
			notaRepository.createFromXml(newNotaXml);
*/

		} finally {
			if (database != null) {
				database.close();
				System.out.println("\nConexão com o banco de dados fechada.");
			}
		}
	}
}