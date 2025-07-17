package com.atividade.odm.controller;

import com.atividade.odm.model.Estudante;
import com.atividade.odm.repository.EstudanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/estudantes")
public class EstudanteController {
    @Autowired
    private  EstudanteRepository estudanteRepository;

    @GetMapping(value = "/all")
    public ResponseEntity<List<Estudante>> findAll(){
        return ResponseEntity.ok(estudanteRepository.findAll());
    }
    @PostMapping
    public ResponseEntity<Estudante> insert(@RequestBody Estudante e){
        Estudante entity = estudanteRepository.save(e);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(entity.getId()).toUri();
        return ResponseEntity.created(uri).body(entity);
    }

}
