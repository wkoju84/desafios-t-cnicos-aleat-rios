package com.example.alunos.controllers;

import com.example.alunos.dtos.AlunoRequest;
import com.example.alunos.dtos.AlunoResponse;
import com.example.alunos.dtos.MatriculaDTO;
import com.example.alunos.services.AlunoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @PostMapping
    public ResponseEntity<AlunoResponse> criar(@RequestBody AlunoRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(alunoService.salvar(request));
    }

    @GetMapping
    public List<AlunoResponse> listarTodos(){
        return alunoService.listarTodos();
    }

    @GetMapping("/{id}/matriculas")
    public List<MatriculaDTO> listarMatriculas(@PathVariable Long id){
        return alunoService.listarMatriculas(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoResponse> atualizar(@PathVariable Long id, @RequestBody AlunoRequest request){
        return ResponseEntity.ok(alunoService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id){
        alunoService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
