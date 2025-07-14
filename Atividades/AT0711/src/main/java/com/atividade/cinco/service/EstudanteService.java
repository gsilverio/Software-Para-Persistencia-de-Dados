package com.atividade.cinco.service;

import com.atividade.cinco.model.Disciplina;
import com.atividade.cinco.model.Estudante;
import com.atividade.cinco.model.Turma;
import com.atividade.cinco.repository.EstudanteRepository;
import com.atividade.cinco.repository.TurmaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EstudanteService extends AbstractCrudService<Estudante, Integer>{

    @Autowired
    private EstudanteRepository estudanteRepository;

    @Override
    protected JpaRepository<Estudante, Integer> getRepository(){
        return  estudanteRepository;
    }

    @Transactional
    public Estudante updateEstudante(Integer id, String nomeCompleto, String dataDeNascimento) {
        // 1. Carregar o estudante
        Estudante estudanteExistente = getRepository().findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estudante com id " + id + " não encontrado."));

        // 2. Atualizar os campos
        if (nomeCompleto != null && !nomeCompleto.isBlank()) {
            estudanteExistente.setNomeCompleto(nomeCompleto);
        }

        if (dataDeNascimento != null && !dataDeNascimento.isBlank()) {
            estudanteExistente.setDataDeNascimento(dataDeNascimento);
        }

        // O campo 'matricula' (número) provavelmente não deve ser alterado aqui.

        // 3. Salvar e retornar
        return getRepository().save(estudanteExistente);
    }

    public List<Estudante> findAll(){
        return estudanteRepository.findAll();
    }
}
