package com.example.alunos.mappers;

import com.example.alunos.dtos.AlunoRequest;
import com.example.alunos.dtos.AlunoResponse;
import com.example.alunos.dtos.MatriculaDTO;
import com.example.alunos.entities.Aluno;
import com.example.alunos.entities.Matricula;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AlunoMapper {

    public Aluno toEntity(AlunoRequest request){

        Aluno aluno = new Aluno();
        aluno.setNome(request.nome());
        aluno.setTelefone(request.telefone());
        aluno.setDataNascimento(request.dataNascimento());

        List<Matricula> matriculas = request.matriculas().stream().map(matriculaDTO -> {
            Matricula matricula = new Matricula();
            matricula.setCodigoMatricula(matriculaDTO.codigoMatricula());
            matricula.setDataInicio(matriculaDTO.dataInicio());
            matricula.setNomeCurso(matriculaDTO.nomeCurso());

            return matricula;
        }).toList();

        aluno.setMatriculas(matriculas);

        return aluno;
    }

    public AlunoResponse toResponse(Aluno aluno){

        List<MatriculaDTO> matriculaDTOS = aluno.getMatriculas().stream().map(matricula ->
                new MatriculaDTO(
                        matricula.getCodigoMatricula(),
                        matricula.getNomeCurso(),
                        matricula.getDataInicio())).toList();

        return new AlunoResponse(
                aluno.getId(),
                aluno.getNome(),
                aluno.getTelefone(),
                aluno.getDataNascimento(),
                matriculaDTOS);
    }
}
