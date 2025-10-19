package com.example.schollink.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.schollink.model.Funcionario;
import com.example.schollink.repository.FuncionarioRepository;

@Service
public class FuncionarioService {
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public Funcionario cadastrarFuncionario(Funcionario funcionario){
       if(funcionario.getEmail() != null){
        return funcionarioRepository.save(funcionario);
       }
       return null;
    }
}
