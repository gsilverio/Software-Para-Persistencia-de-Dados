package com.atividade.odm.model;

import com.atividade.odm.repository.EstudanteRepository;
import com.atividade.odm.repository.TurmaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@DataMongoTest
class EstudanteRepositoryTest {
    @Autowired
    private EstudanteRepository repo;
    @Autowired
    private TurmaRepository turmaRepository;

    @Test
    void testSalvarEConsultar() {
        // Cria e salva um estudante
        Estudante e = new Estudante("Maria Teste", "1990-01-01", 12345);
        Estudante salvo = repo.save(e);

        // Verifica se o ID foi gerado
        assertNotNull(salvo.getId());

        // Busca todos os estudantes
        List<Estudante> lista = repo.findAll();

        // Verifica se há pelo menos um
        assertTrue(lista.size() > 0);
    }

    @Test
    void testSalvarEConsultarTurma() {
        // Cria e salva um estudante
        Turma e = new Turma("A", "202506154", 30);
        Turma turma = turmaRepository.save(e);

        // Verifica se o ID foi gerado
        assertNotNull(turma.getId());

        // Busca todos os estudantes
        List<Turma> lista = turmaRepository.findAll();

        // Verifica se há pelo menos um
        assertTrue(lista.size() > 0);
    }
}
