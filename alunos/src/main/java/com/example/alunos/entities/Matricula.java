package com.example.alunos.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Matricula extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigoMatricula;
    private String nomeCurso;
    private LocalDate dataInicio;

    @ManyToMany
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;
}
