package com.atividade.cinco.service;

import com.atividade.cinco.model.Disciplina;
import com.atividade.cinco.model.Matricula;
import com.atividade.cinco.model.Turma;
import com.atividade.cinco.repository.DisciplinaRepository;
import com.atividade.cinco.repository.MatriculaRepository;
import com.atividade.cinco.repository.TurmaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TurmaService extends AbstractCrudService<Turma, Integer>{

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Override
    protected JpaRepository<Turma, Integer> getRepository(){
        return  turmaRepository;
    }

    @Override
    public List<Turma> loadAll(){
        List<Turma> turmas = super.loadAll();
        turmas.forEach(turma->{
            long count = matriculaRepository.countByTurmaIdAndStatusAtivo(turma.getId());
            turma.setAlunosMatriculados((int) count);
        });
        return turmas;
    }

    @Transactional
    public Turma updateTurma(Integer id, String novoCodigo, Integer novasVagas, Integer novaDisciplinaId) {
        // 1. Carregar a turma
        Turma turmaExistente = turmaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Turma com id " + id + " não encontrada."));

        // 2. Atualizar código (com validação)
        if (novoCodigo != null && !novoCodigo.isBlank()) {
            turmaExistente.setCodigo(novoCodigo);
        }

        // 3. Atualizar vagas (com validação)
        // Regra de negócio: não se pode diminuir o total de vagas para menos do que os alunos já matriculados.
        long alunosMatriculados = turmaRepository.countByTurmaIdAndStatusAtivo(id); // Supondo um método no repositório
        if (novasVagas != null && novasVagas < alunosMatriculados) {
            throw new IllegalStateException("O número de vagas não pode ser menor que o de alunos já matriculados (" + alunosMatriculados + ").");
        }
        if (novasVagas != null) {
            turmaExistente.setVagasDisponiveis(novasVagas);
        }

        // 4. Atualizar a disciplina
        if (novaDisciplinaId != null) {
            Disciplina novaDisciplina = disciplinaRepository.findById(novaDisciplinaId)
                    .orElseThrow(() -> new EntityNotFoundException("Disciplina com id " + novaDisciplinaId + " não encontrada."));
            turmaExistente.setDisciplina(novaDisciplina);
        }

        // 5. Salvar e retornar
        return turmaRepository.save(turmaExistente);
    }

    @Override
    public Turma loadFromId(Integer id){
        return turmaRepository.findByIdWithDisciplina(id).orElseThrow(()-> new EntityNotFoundException("Turma com id "+ id + "não encontrada."));
    }
}
