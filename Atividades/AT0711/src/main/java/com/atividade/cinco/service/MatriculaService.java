package com.atividade.cinco.service;

import com.atividade.cinco.events.TurmasChangedEvent;
import com.atividade.cinco.model.Disciplina;
import com.atividade.cinco.model.Matricula;
import com.atividade.cinco.model.Turma;
import com.atividade.cinco.model.enuns.StatusMatricula;
import com.atividade.cinco.repository.DisciplinaRepository;
import com.atividade.cinco.repository.MatriculaRepository;
import com.atividade.cinco.repository.TurmaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MatriculaService extends AbstractCrudService<Matricula, Integer>{

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    private TurmaRepository  turmaRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Override
    protected JpaRepository<Matricula, Integer> getRepository(){
        return  matriculaRepository;
    }

    @Override
    public Matricula create(Matricula entity){
        Matricula matriculaSalva = super.create(entity);
        eventPublisher.publishEvent(new TurmasChangedEvent(this));
        return matriculaSalva;
    }
    @Override
    public void delete(Matricula entity){
        super.delete(entity);
        eventPublisher.publishEvent(new TurmasChangedEvent(this));

    }

    @Transactional
    public Matricula updateStatusMatricula(Integer matriculaId, StatusMatricula novoStatus) {
        // 1. Carregar a matrícula
        Matricula matricula = matriculaRepository.findById(matriculaId)
                .orElseThrow(() -> new EntityNotFoundException("Matrícula com id " + matriculaId + " não encontrada."));

        StatusMatricula statusAntigo = matricula.getStatus();

        // Se o status não mudou, não faz nada.
        if (statusAntigo == novoStatus) {
            return matricula;
        }

        Turma turma = matricula.getTurma();
        long alunosMatriculados = turmaRepository.countByTurmaIdAndStatusAtivo(turma.getId());

        // 2. Aplicar regras de negócio
        if (novoStatus == StatusMatricula.CONFIRMADA) {
            // Regra: Não pode ativar uma matrícula se a turma estiver cheia
            if (alunosMatriculados >= turma.getVagasDisponiveis()) {
                throw new IllegalStateException("Não é possível ativar a matrícula. A turma " + turma.getCodigo() + " está cheia.");
            }
        }

        // 3. Atualizar o status
        matricula.setStatus(novoStatus);

        // O ideal é que o número de alunos matriculados na turma seja sempre calculado
        // via uma query (`COUNT`) em vez de um campo numérico, para evitar inconsistências.
        // O campo `alunosMatriculados` na entidade Turma é @Transient, o que é ótimo!
        // Assim, nenhuma atualização é necessária na entidade Turma.

        // 4. Salvar e retornar
        return matriculaRepository.save(matricula);
    }

}
