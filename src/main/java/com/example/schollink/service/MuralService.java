package com.example.schollink.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.schollink.Dto.AvisoDto;
import com.example.schollink.model.Aluno;
import com.example.schollink.model.Aviso;
import com.example.schollink.model.TurmaDisciplina;
import com.example.schollink.repository.AlunoRepository;
import com.example.schollink.repository.AvisoRepository;
import com.example.schollink.repository.TurmaDisciplinaRepository;

@Service
public class MuralService {
    @Autowired
    private AvisoRepository avisoRepository;
    @Autowired
    private TurmaDisciplinaRepository turmaDisciplinaRepository;
    @Autowired
    private AlunoRepository alunoRepository;
    public boolean cadastrarAviso(AvisoDto dto){
      Optional<TurmaDisciplina> turmaDisciplinaOpt = turmaDisciplinaRepository.findById(dto.getIdTurmaDisciplina());
      if(turmaDisciplinaOpt.isEmpty()){
        return false;
      }
      TurmaDisciplina turmaDisciplina = turmaDisciplinaOpt.get();
      Aviso aviso = new Aviso();
      aviso.setMensagem(dto.getMensagem());
      aviso.setTurmaDisciplina(turmaDisciplina);
      avisoRepository.save(aviso);
      return true;
    }

    public List<AvisoDto> buscarAvisos(Long idUser){
       Optional<Aluno> alunoOpt = alunoRepository.findByUserId(idUser);
       if(alunoOpt.isEmpty()){
        return new ArrayList<>();
       }
       Aluno aluno = alunoOpt.get();
       List<Aviso> avisos = avisoRepository.findByTurmaDisciplina_Turma(aluno.getTurma());
       List<AvisoDto> avisosDtos = new ArrayList<>();
       for (Aviso aviso : avisos) {
        AvisoDto avisoDto = new AvisoDto();
        avisoDto.setId(aviso.getId());
        avisoDto.setMensagem(aviso.getMensagem());
        avisoDto.setIdTurmaDisciplina(aviso.getTurmaDisciplina().getId());
        avisoDto.setNomeProfessor(aviso.getTurmaDisciplina().getProfessor().getUser().getNome());
        avisosDtos.add(avisoDto);
       }
       return avisosDtos;
    }
}
