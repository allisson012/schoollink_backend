package com.example.schollink.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties.Http;
import org.springframework.stereotype.Service;

import com.example.schollink.Dto.AdminDto;
import com.example.schollink.Dto.AvisoDto;
import com.example.schollink.model.Admin;
import com.example.schollink.model.Aluno;
import com.example.schollink.model.Aviso;
import com.example.schollink.model.Turma;
import com.example.schollink.model.User;
import com.example.schollink.model.UserRole;
import com.example.schollink.repository.AdminRepository;
import com.example.schollink.repository.AlunoRepository;
import com.example.schollink.repository.AvisoRepository;
import com.example.schollink.repository.TurmaRepository;
import com.example.schollink.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class MuralService {
    @Autowired
    private AvisoRepository avisoRepository;
    @Autowired
    private TurmaRepository turmaRepository;
    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdminRepository adminRepository;

    public boolean cadastrarAviso(AvisoDto dto, HttpSession session){
      if(session == null){
        return false;
      }
      Optional<Turma> turmaOpt = turmaRepository.findById(dto.getIdTurma());
      if(turmaOpt.isEmpty()){
        return false;
      }
      Turma turma = turmaOpt.get();
      Aviso aviso = new Aviso();
      aviso.setMensagem(dto.getMensagem());
      aviso.setTurma(turma);
      if(session.getAttribute("UserRole") == UserRole.ADMIN){
        Admin admin = new Admin();
        admin.setId_admin((Long) session.getAttribute("userId"));
        aviso.setAdmin(admin);
        aviso.setUser(null);
      }
      if(session.getAttribute("UserRole") == UserRole.PROFESSOR){
        aviso.setAdmin(null);
        User user = new User();
        user.setId((Long) session.getAttribute("userId"));
        aviso.setUser(user);

      }
      avisoRepository.save(aviso);
      return true;
    }

    public List<AvisoDto> buscarAvisos(HttpSession session){
       if(session.getAttribute("UserRole") == UserRole.ADMIN || session.getAttribute("UserRole") == UserRole.PROFESSOR){
          List<Aviso> avisos = avisoRepository.findAll();
          List<AvisoDto> avisoDtos = new ArrayList<>();
          for(Aviso aviso : avisos){
              AvisoDto dto = new AvisoDto();
              dto.setId(aviso.getId());
              dto.setMensagem(aviso.getMensagem());
              dto.setIdTurma(aviso.getTurma().getId());
              if(aviso.getUser() != null){
                dto.setNomeProfessor(aviso.getUser().getNome());
              }
              if(aviso.getAdmin() != null){
                  AdminDto adminDto = new AdminDto();
                  adminDto.setId(aviso.getAdmin().getId_admin());
                  dto.setAdminDto(adminDto);
              }
              avisoDtos.add(dto);
          }
          return avisoDtos; 
       }
       else if(session.getAttribute("UserRole") == UserRole.ALUNO){
          Long userId = (Long) session.getAttribute("userId");
          Optional<Aluno> alunoOpt = alunoRepository.findByUserId(userId);
          if(alunoOpt.isEmpty()){
              return null;
          }
          Aluno aluno = alunoOpt.get();
          Long turmaId = aluno.getTurma().getId();
          List<Aviso> avisos = avisoRepository.findByTurmaId(turmaId);
          List<AvisoDto> avisoDtos = new ArrayList<>();
          for(Aviso aviso : avisos){
              AvisoDto dto = new AvisoDto();
              dto.setId(aviso.getId());
              dto.setMensagem(aviso.getMensagem());
              dto.setIdTurma(aviso.getTurma().getId());
              if(aviso.getUser() != null){
                dto.setNomeProfessor(aviso.getUser().getNome());
              }

              if(aviso.getAdmin() != null){
                  AdminDto adminDto = new AdminDto();
                  adminDto.setId(aviso.getAdmin().getId_admin());
                  dto.setAdminDto(adminDto);
              }
              avisoDtos.add(dto);
          }
          return avisoDtos;
       }
       return null;
    }
}
