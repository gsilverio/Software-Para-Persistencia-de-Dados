package com.atividade.cinco.repository;

import com.atividade.cinco.model.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Integer> {

    @Query("SELECT COUNT(m) FROM Matricula m WHERE m.turma.id = :turmaId AND m.status = com.atividade.cinco.model.enuns.StatusMatricula.CONFIRMADA")
    long countByTurmaIdAndStatusAtivo(@Param("turmaId") Integer turmaId);

    @Query("SELECT t FROM Turma t JOIN FETCH t.disciplina WHERE t.id=:id")
    Optional<Turma> findByIdWithDisciplina(@Param("id") Integer id);

}
