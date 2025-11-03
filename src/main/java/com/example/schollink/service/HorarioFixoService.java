package com.example.schollink.service;

import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.schollink.Dto.HorarioFixoDto;
import com.example.schollink.model.Aluno;
import com.example.schollink.model.HorarioFixo;
import com.example.schollink.repository.AlunoRepository;
import com.example.schollink.repository.HorarioFixoRepository;

@Service
public class HorarioFixoService {
    @Autowired
    private HorarioFixoRepository horarioFixoRepository;
    @Autowired
    private AlunoRepository alunoRepository;

    public HorarioFixo cadastrarHorarioFixo(HorarioFixo horarioFixo) {
        HorarioFixo salvo = horarioFixoRepository.save(horarioFixo);
        return salvo;
    }

    public List<HorarioFixoDto> buscarHorarioAluno(Long idUser) {
        Optional<Aluno> alunoOpt = alunoRepository.findByUserId(idUser);
        if (alunoOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado para o usuário informado");
        }
        Aluno aluno = alunoOpt.get();
        List<HorarioFixo> horarioFixos = new ArrayList();
        horarioFixos = horarioFixoRepository.findByTurma(aluno.getTurma());
        if (horarioFixos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Nenhum horário cadastrado para a turma do aluno");
        }
        List<HorarioFixoDto> dtos = new ArrayList<>();
        for (HorarioFixo horarioFixo : horarioFixos) {
            HorarioFixoDto dto = new HorarioFixoDto();
            dto.setDisciplinaNome(horarioFixo.getDisciplina().getNome());
            dto.setTurmaNome(horarioFixo.getTurma().getNome());
            dto.setHorarioInicio(horarioFixo.getHoraInicio().toString());
            dto.setHorarioTermino(horarioFixo.getHoraFim().toString());
            dto.setDiaDaSemana(horarioFixo.getDiaSemana().getDisplayName(TextStyle.FULL, new Locale("pt", "BR")));
            dtos.add(dto);
        }

        return dtos;
    }
}
