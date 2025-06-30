package com.example.alunos.services;

import com.example.alunos.dtos.AlunoRequest;
import com.example.alunos.dtos.AlunoResponse;
import com.example.alunos.dtos.MatriculaDTO;
import com.example.alunos.entities.Aluno;
import com.example.alunos.entities.Matricula;
import com.example.alunos.mappers.AlunoMapper;
import com.example.alunos.repositories.AlunoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final AlunoMapper alunoMapper;

    public AlunoService(AlunoRepository alunoRepository, AlunoMapper alunoMapper) {
        this.alunoRepository = alunoRepository;
        this.alunoMapper = alunoMapper;
    }

    public AlunoResponse salvar(AlunoRequest request){
        Aluno aluno = alunoMapper.toEntity(request);
        alunoRepository.save(aluno);
        return alunoMapper.toResponse(aluno);
    }

    public List<AlunoResponse> listarTodos(){

        return alunoRepository.findAll().stream().map(alunoMapper::toResponse).toList();
    }

    public List<MatriculaDTO> listarMatriculas(Long id){
        Aluno aluno = alunoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "Aluno não encontrado"));

        return aluno.getMatriculas().stream().map(matricula -> new MatriculaDTO(
                matricula.getCodigoMatricula(),
                matricula.getNomeCurso(),
                matricula.getDataInicio()
        )).toList();
    }

    public void remover(Long id){
        if (!alunoRepository.existsById(id)){
            throw new EntityNotFoundException("ID do aluno não encontrado");
        }
        alunoRepository.deleteById(id);
    }

    public AlunoResponse atualizar(Long id, AlunoRequest request){

        Aluno a = alunoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "Aluno não encontrado"));

        a.setNome(request.nome());
        a.setTelefone(request.telefone());
        a.setDataNascimento(request.dataNascimento());

        for (MatriculaDTO m : request.matriculas()){
            Matricula matricula = new Matricula();
            matricula.setCodigoMatricula(m.codigoMatricula());
            matricula.setDataInicio(m.dataInicio());
            matricula.setNomeCurso(m.nomeCurso());
            a.getMatriculas().add(matricula);
        }

        return alunoMapper.toResponse(alunoRepository.save(a));
    }
}
