package com.example.schollink.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.schollink.Dto.AlunoDto;
import com.example.schollink.Dto.EnderecoDto;
import com.example.schollink.Dto.UserDto;
import com.example.schollink.model.Aluno;
import com.example.schollink.model.Endereco;
import com.example.schollink.model.StatusMatricula;
import com.example.schollink.model.User;
import com.example.schollink.model.UserRole;
import com.example.schollink.repository.AlunoRepository;
import com.example.schollink.repository.UserRepository;

@Service
public class AlunoService {
    @Autowired
    private PasswordService passwordService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AlunoRepository alunoRepository;

    public void cadastrarAluno(User user, Aluno aluno, String senha) {
        byte salt[] = passwordService.gerarSalt();
        byte hash[] = passwordService.gerarHash(senha, salt);
        user.setSalt(salt);
        user.setHash(hash);
        user.setUserRole(UserRole.ALUNO);
        User userCreate = userRepository.save(user);
        aluno.setUser(userCreate);
        alunoRepository.save(aluno);
    }

    public Aluno editarAluno(AlunoDto alunoDto, Long id) {
        Optional<Aluno> alunoOpt = alunoRepository.findById(id);
        if (alunoOpt.isEmpty()) {
            throw new RuntimeException("Aluno não encontrado");
        }

        Aluno alunoExistente = alunoOpt.get();
        User userExistente = alunoExistente.getUser();

        if (alunoDto.getMatricula() != null && !alunoDto.getMatricula().isBlank()) {
            alunoExistente.setMatricula(alunoDto.getMatricula());
        }
        if (alunoDto.getDataMatricula() != null) {
            alunoExistente.setDataMatricula(alunoDto.getDataMatricula());
        }
        if (alunoDto.getStatusMatricula() != null && !alunoDto.getStatusMatricula().isBlank()) {
            alunoExistente.setStatusMatricula(
                StatusMatricula.valueOf(alunoDto.getStatusMatricula().trim().toUpperCase())
            );
        }
        if (alunoDto.getNomeResponsavel() != null && !alunoDto.getNomeResponsavel().isBlank()) {
            alunoExistente.setNomeResponsavel(alunoDto.getNomeResponsavel());
        }
        if (alunoDto.getTelefoneResponsavel() != null && !alunoDto.getTelefoneResponsavel().isBlank()) {
            alunoExistente.setTelefoneResponsavel(alunoDto.getTelefoneResponsavel());
        }

        if (alunoDto.getUserDto() != null) {
            UserDto userDto = alunoDto.getUserDto();

            if (userDto.getNome() != null && !userDto.getNome().isBlank()) {
                userExistente.setNome(userDto.getNome());
            }
            if (userDto.getEmail() != null && !userDto.getEmail().isBlank()) {
                userExistente.setEmail(userDto.getEmail());
            }
            if (userDto.getCpf() != null && !userDto.getCpf().isBlank()) {
                userExistente.setCpf(userDto.getCpf());
            }
            if (userDto.getDataNascimento() != null) {
                userExistente.setDataNascimento(userDto.getDataNascimento());
            }
            if (userDto.getGenero() != null && !userDto.getGenero().isBlank()) {
                userExistente.setGenero(userDto.getGenero());
            }
            if (userDto.getTelefone() != null && !userDto.getTelefone().isBlank()) {
                userExistente.setTelefone(userDto.getTelefone());
            }

            // Atualiza o endereço (caso tenha vindo no DTO)
            if (alunoDto.getEnderecoDto() != null) {
                EnderecoDto enderecoDto = alunoDto.getEnderecoDto();
                if (userExistente.getEndereco() == null) {
                    userExistente.setEndereco(new Endereco());
                }
                Endereco endereco = userExistente.getEndereco();

                if (enderecoDto.getCep() != null && !enderecoDto.getCep().isBlank()) {
                    endereco.setCep(enderecoDto.getCep());
                }
                if (enderecoDto.getPais() != null && !enderecoDto.getPais().isBlank()) {
                    endereco.setPais(enderecoDto.getPais());
                }
                if (enderecoDto.getEstado() != null && !enderecoDto.getEstado().isBlank()) {
                    endereco.setEstado(enderecoDto.getEstado());
                }
                if (enderecoDto.getCidade() != null && !enderecoDto.getCidade().isBlank()) {
                    endereco.setCidade(enderecoDto.getCidade());
                }
                if (enderecoDto.getRua() != null && !enderecoDto.getRua().isBlank()) {
                    endereco.setRua(enderecoDto.getRua());
                }
                if (enderecoDto.getNumero() != null && !enderecoDto.getNumero().isBlank()) {
                    endereco.setNumero(enderecoDto.getNumero());
                }
            }

            userRepository.save(userExistente);
        }

        return alunoRepository.save(alunoExistente);
    }

    public void excluirAluno(Long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
        
        if(aluno.getStatusMatricula().equals(StatusMatricula.ATIVA)){
            aluno.setStatusMatricula(StatusMatricula.INATIVA);
            alunoRepository.save(aluno);
        }
    }

    public Aluno verAluno(Long id){        
        Aluno aluno = alunoRepository.findByUserId(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        return aluno;
    }

     public Optional<List<Aluno>> buscar(String nome, String matricula, String email) {
        List<Aluno> alunos = new ArrayList<>();

        if (email != null && !email.isEmpty()) {
            alunos = alunoRepository.findByUserEmailContainingIgnoreCase(email);
        } else if (matricula != null && !matricula.isEmpty()) {
            alunos = alunoRepository.findByMatriculaContainingIgnoreCase(matricula);
        } else if (nome != null && !nome.isEmpty()) {
            alunos = alunoRepository.findByUserNomeContainingIgnoreCase(nome);
        }

        return alunos.isEmpty() ? Optional.empty() : Optional.of(alunos);
    }

}
