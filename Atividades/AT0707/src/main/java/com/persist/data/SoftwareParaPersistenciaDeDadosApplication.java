package com.persist.data;

import com.persist.data.database.Database;
import com.persist.data.entities.Nota;
import com.persist.data.entities.Student;
import com.persist.data.repositories.NotaRepository;
import com.persist.data.repositories.StudentRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class SoftwareParaPersistenciaDeDadosApplication {

	public static void main(String[] args) throws SQLException {

		Database database = null;

		try {
			database = new Database("teste.db");
			StudentRepository studentRepository = new StudentRepository(database);
			NotaRepository notaRepository = new NotaRepository(database);

			/*Student student = new Student("Guilherme Silverio", 12345, new Date());
			studentRepository.create(student);

			Student student1 = new Student("Marianna Alves", 678910, new Date());
			studentRepository.create(student1);

			Nota nota = new Nota(new BigDecimal("7.50"), null, new Date());
			nota.setStudent(student);
			notaRepository.createNota(nota, student);

			Nota nota1 = new Nota(new BigDecimal("10.00"), "Parabens pela nota!", new Date());
			nota1.setStudent(student1);
			notaRepository.createNota(nota1, student1);

			Nota nota = notaRepository.loadFromId(1);

			Student studentUpdated = studentRepository.loadFromId(1);
			studentUpdated.setFullName("Jeff Buckley");
			studentUpdated.setRegistration(101112);

			studentRepository.update(studentUpdated);

			Nota notaUpdated = notaRepository.loadFromId(1);
			notaUpdated.setNota(new BigDecimal("2.00"));
			notaUpdated.setObs("Se prepare melhor!");

			notaRepository.update(notaUpdated);*/

			notaRepository.delete(1);

			studentRepository.delete(2);



		} finally {
			if (database != null) {
				database.close();
				System.out.println("\nConexão com o banco de dados fechada.");
			}
		}
	}
}