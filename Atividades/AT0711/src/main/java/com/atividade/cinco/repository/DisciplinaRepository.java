package com.atividade.cinco.repository;

import com.atividade.cinco.model.Disciplina;
import com.atividade.cinco.model.Estudante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, Integer> {
    boolean existsByCodigo(String novoCodigo);
}
