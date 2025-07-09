package com.persist.data;

import com.persist.data.database.Database;
import com.persist.data.repositories.NotaRepository;
import com.persist.data.repositories.StudentRepository;
import jakarta.xml.bind.JAXBException;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;


@SpringBootApplication
public class SoftwareParaPersistenciaDeDadosApplication {

	public static void main(String[] args) throws SQLException, JAXBException {
		Database database = null;
		try {
			database = new Database("teste.db");
			StudentRepository studentRepository = new StudentRepository(database);
			NotaRepository notaRepository = new NotaRepository(database);


		} finally {
			if (database != null) {
				database.close();
				System.out.println("\nConexão com o banco de dados fechada.");
			}
		}
	}
}