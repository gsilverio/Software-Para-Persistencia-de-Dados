package com.persist.data;

import com.persist.data.database.Database;
import com.persist.data.entities.Nota;
import com.persist.data.entities.Student;
import com.persist.data.repositories.NotaRepository;
import com.persist.data.repositories.StudentRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;
import java.util.Date;

@SpringBootApplication
public class SoftwareParaPersistenciaDeDadosApplication {

	public static void main(String[] args) throws SQLException {

		Database database = null;

		try {
			database = new Database("teste.db");
			StudentRepository studentRepository = new StudentRepository(database);

			NotaRepository notaRepository = new NotaRepository(database);


			Student student = new Student("Guilherme Silverio", 12345, new Date());
			studentRepository.create(student);

			Nota nota = new Nota(7.5, null);
			nota.setStudent(student);
			notaRepository.createNota(nota, student);



			System.out.println("Estudante criado");


		} finally {
			// 6. Feche a conexão com o banco
			if (database != null) {
				database.close();
				System.out.println("\nConexão com o banco de dados fechada.");
			}
		}
	}
}