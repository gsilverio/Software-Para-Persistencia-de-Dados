package com.atividade.odm.controller;

import com.atividade.odm.model.Turma;
import com.atividade.odm.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turmas")
public class TurmaController {
    @Autowired
    private TurmaRepository turmaRepository;
    @GetMapping
    public ResponseEntity<List<Turma>> findAll(){
        return ResponseEntity.ok(turmaRepository.findAll());
    }
    @PostMapping
    public ResponseEntity<Turma> insert(@RequestBody Turma turma){
        Turma entity = turmaRepository.save(turma);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(entity.getId()).toUri();
        return ResponseEntity.created(uri).body(entity);
    }
    @PutMapping
    public ResponseEntity<Turma> update(@RequestBody Turma dto){
        Turma entity = new Turma();
        entity.setId(dto.getId());
        entity.setCodigoTurma(dto.getCodigoTurma());
        entity.setNomeTurma(dto.getNomeTurma());
        entity.setQuantidadeAlunos(dto.getQuantidadeAlunos());
        turmaRepository.save(entity);
        return ResponseEntity.ok(entity);
    }
    @DeleteMapping("/{turmaId}")
    public ResponseEntity<String> delete(@PathVariable String turmaId){
        Optional<Turma> optionalTurma = turmaRepository.findById(turmaId);
        if(optionalTurma.isPresent()){
            turmaRepository.delete(optionalTurma.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
