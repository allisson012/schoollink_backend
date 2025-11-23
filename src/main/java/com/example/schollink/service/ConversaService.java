package com.example.schollink.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.schollink.Dto.MensagemDto;
import com.example.schollink.model.Aluno;
import com.example.schollink.model.Conversa;
import com.example.schollink.model.Mensagem;
import com.example.schollink.model.UserRole;
import com.example.schollink.repository.AlunoRepository;
import com.example.schollink.repository.ConversaRepository;
import com.example.schollink.repository.MensagemRepository;

@Service
public class ConversaService {
    @Autowired
    private ConversaRepository conversaRepository;
    @Autowired
    private MensagemRepository mensagemRepository;
    @Autowired
    private AlunoRepository alunoRepository;

    public boolean enviarMensagem(UserRole userRole, MensagemDto dto) {
        Mensagem mensagem = new Mensagem();
        Optional<Aluno> alunoOpt = alunoRepository.findById(dto.getIdAluno());
        if (alunoOpt.isEmpty()) {
            return false;
        }
        Aluno aluno = alunoOpt.get();
        if (userRole.equals(UserRole.ADMIN)) {
            mensagem.setTipo("ADMIN");
        } else if (userRole.equals(UserRole.ALUNO)) {
            mensagem.setTipo("ALUNO");
        } else {
            return false;
        }
        Optional<Conversa> conversaOpt = conversaRepository.findByAluno(aluno);
        Conversa conversa = new Conversa();
        if (conversaOpt.isEmpty()) {
            conversa.setAluno(aluno);
            conversa.setCriadoEm(new Timestamp(System.currentTimeMillis()));
            conversa = conversaRepository.save(conversa);

        } else {
            conversa = conversaOpt.get();
        }
        mensagem.setConversa(conversa);
        mensagem.setEnviadoEm(new Timestamp(System.currentTimeMillis()));
        mensagem.setIdRementente(dto.getIdRemetente());
        mensagem.setMensagem(dto.getMensagem());
        mensagemRepository.save(mensagem);
        return true;
    }

    public List<MensagemDto> buscarMensagens(Long idConversa){
        Optional<Conversa> conversaOpt = conversaRepository.findById(idConversa);
        if(conversaOpt.isEmpty()){
            return null;
        }
        Conversa conversa = conversaOpt.get();
        List<Mensagem> mensagens = conversa.getMensagens();
        List<MensagemDto> dtos = new ArrayList<>();
        for (Mensagem mensagem : mensagens) {
            MensagemDto dto = new MensagemDto();
            if(mensagem.getTipo().equals("ALUNO")){
            dto.setNomeAluno(mensagem.getConversa().getAluno().getUser().getNome());
            dto.setIdAluno(mensagem.getConversa().getAluno().getIdAluno());
            }
            dto.setMensagem(mensagem.getMensagem());
            dto.setIdRemetente(mensagem.getIdRementente());
            dto.setIdMensagem(mensagem.getId());
            dtos.add(dto);
        }
        return dtos;
    }
}
