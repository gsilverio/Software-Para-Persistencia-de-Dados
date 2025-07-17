package com.atividade.odm.repository;

import com.atividade.odm.model.Estudante;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudanteRepository extends MongoRepository<Estudante, String> {
}
