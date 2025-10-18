package com.example.schollink.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.schollink.model.Turma;
import com.example.schollink.repository.TurmaRepository;

@Service
public class TurmaService {
    @Autowired
    private TurmaRepository turmaRepository;
    public void cadastrarTurma(Turma turma){
        if(turma != null)
        turmaRepository.save(turma);
    }
}
