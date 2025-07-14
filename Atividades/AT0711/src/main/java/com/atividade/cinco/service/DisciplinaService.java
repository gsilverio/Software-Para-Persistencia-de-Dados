package com.atividade.cinco.service;

import com.atividade.cinco.model.Disciplina;
import com.atividade.cinco.repository.DisciplinaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DisciplinaService extends AbstractCrudService<Disciplina, Integer>{

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Override
    protected JpaRepository<Disciplina, Integer> getRepository(){return disciplinaRepository;}

    @Transactional
    public Disciplina updateDisciplina(Integer id, String novoCodigo, String novoTitulo, String novaEmenta) {
        // 1. Carregar a entidade existente
        Disciplina disciplinaExistente = disciplinaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Disciplina com id " + id + " não encontrada."));

        // 2. Aplicar validações (exemplo)
        if (novoCodigo != null && !novoCodigo.isBlank() && !disciplinaExistente.getCodigo().equals(novoCodigo)) {
            if (disciplinaRepository.existsByCodigo(novoCodigo)) {
                throw new IllegalStateException("Código " + novoCodigo + " já está em uso.");
            }
            disciplinaExistente.setCodigo(novoCodigo);
        }

        // 3. Atualizar os campos
        if (novoTitulo != null && !novoTitulo.isBlank()) {
            disciplinaExistente.setTitulo(novoTitulo);
        }

        if (novaEmenta != null && !novaEmenta.isBlank()) {
            disciplinaExistente.setEmenta(novaEmenta);
        }

        return disciplinaRepository.save(disciplinaExistente);
    }

    public List<Disciplina> findAll(){
        return disciplinaRepository.findAll();
    }
}
