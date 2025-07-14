package com.atividade.cinco.repository;

import com.atividade.cinco.model.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Integer> {
    @Query("SELECT COUNT(m) FROM Matricula m WHERE m.turma.id = :turmaId AND m.status = com.atividade.cinco.model.enuns.StatusMatricula.CONFIRMADA")
    long countByTurmaIdAndStatusAtivo(@Param("turmaId") Integer turmaId);
}
