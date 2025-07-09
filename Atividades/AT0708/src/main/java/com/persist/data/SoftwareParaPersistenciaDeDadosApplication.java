package com.persist.data;

import com.google.gson.Gson;
import com.persist.data.database.Database;
import com.persist.data.dto.NotaDTO;
import com.persist.data.entities.*;
import com.persist.data.repositories.NotaRepository;
import com.persist.data.repositories.StudentRepository;
import jakarta.xml.bind.JAXBException;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;


@SpringBootApplication
public class SoftwareParaPersistenciaDeDadosApplication {

	public static void main(String[] args) throws SQLException, JAXBException {

		Database database = null;

		try {
			database = new Database("teste.db");
			StudentRepository studentRepository = new StudentRepository(database);
			NotaRepository notaRepository = new NotaRepository(database);

			NotaSerializer notaJsonSerializer = new NotaSerializer();

			/*Student student = new Student("Guilherme Silverio", 12345, new Date());
			studentRepository.create(student);

			Student student1 = new Student("Marianna Alves", 678910, new Date());
			studentRepository.create(student1);

			Nota nota = new Nota(new BigDecimal("7.50"), null, new Date());
			nota.setStudent(student);
			notaRepository.createNota(nota, student);

			Nota nota1 = new Nota(new BigDecimal("10.00"), "Parabens pela nota!", new Date());
			nota1.setStudent(student1);
			notaRepository.createNota(nota1, student1);*/

			/*
			Nota nota = notaRepository.loadFromId(1);

			Student studentUpdated = studentRepository.loadFromId(1);
			studentUpdated.setFullName("Jeff Buckley");
			studentUpdated.setRegistration(101112);

			studentRepository.update(studentUpdated);

			Nota notaUpdated = notaRepository.loadFromId(1);
			notaUpdated.setNota(new BigDecimal("2.00"));
			notaUpdated.setObs("Se prepare melhor!");

			notaRepository.update(notaUpdated);*/

			/*Student student4 = studentRepository.loadFromId(1);

			StudentJsonSerializer studentJsonSerializer = new StudentJsonSerializer();
			String json = studentJsonSerializer.toJson(student4);
			System.out.println("Estudante: \n" + json);

//			Student loaded = studentJsonSerializer.fromJson(json);
//			System.out.println("Desserializado: \n" + loaded.getFullName());

			StudentXmlSerializer studentXmlSerializer = new StudentXmlSerializer();
			String xml = studentXmlSerializer.toXml(student4);
			System.out.println("XML: \n" + xml);

			Student loaded1 = studentXmlSerializer.fromXml(xml);
			System.out.println("Desserializado: \n" + loaded1.getFullName());

			List<Student> listStudent = studentRepository.loadAll();

			FileWriter fileWriter = new FileWriter("students.json");


			for (Student s : listStudent) {
				String json1 = studentJsonSerializer.toJson(s);
				fileWriter.write(json1);
			}

			fileWriter.close();
*/

			FileReader fileReader = new FileReader("insert-nota.json");
			NotaDTO notaDTOLoaded = new Gson().fromJson(fileReader, NotaDTO.class);

			Nota nota = new Nota();
			nota.setNota(notaDTOLoaded.getNota());
			nota.setObs(notaDTOLoaded.getObs());
			nota.setDataCorrecao(notaDTOLoaded.getDataCorrecao());
			nota.setStudent(studentRepository.loadFromId(notaDTOLoaded.getStudent_id()));
			notaRepository.createNota(nota, studentRepository.loadFromId(notaDTOLoaded.getStudent_id()));
			fileReader.close();


			notaJsonSerializer.dumpFile("JSON", new File("notas-json.json"));
			notaJsonSerializer.dumpFile("XML", new File("notas-xml.xml"));


		}catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (database != null) {
				database.close();
				System.out.println("\nConexão com o banco de dados fechada.");
			}
		}
	}
}