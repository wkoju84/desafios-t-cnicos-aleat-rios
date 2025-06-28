package com.example.alunos.dtos;

import java.time.LocalDate;

public record MatriculaDTO(String codigoMatricula,
                           String nomeCurso,
                           LocalDate dataInicio) {
}
