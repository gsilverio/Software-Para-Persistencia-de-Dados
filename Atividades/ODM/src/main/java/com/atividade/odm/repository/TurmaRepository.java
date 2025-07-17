package com.atividade.odm.repository;

import com.atividade.odm.model.Estudante;
import com.atividade.odm.model.Turma;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurmaRepository extends MongoRepository<Turma, String> {
}
