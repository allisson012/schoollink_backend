package com.example.schollink.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.schollink.Dto.AlunoParaTurmaDto;
import com.example.schollink.Dto.AulaRetornoDto;
import com.example.schollink.Dto.DataDto;
import com.example.schollink.Dto.DisciplinaProfessorDto;
import com.example.schollink.Dto.AlunoDto;
import com.example.schollink.Dto.EnderecoDto;
import com.example.schollink.Dto.HistoricoAulaDto;
import com.example.schollink.Dto.PresencasAlunoDto;
import com.example.schollink.Dto.UserDto;

import com.example.schollink.model.Aluno;
import com.example.schollink.model.Endereco;
import com.example.schollink.model.HistoricoAula;
import com.example.schollink.model.HorarioAula;
import com.example.schollink.model.Presenca;
import com.example.schollink.model.StatusMatricula;
import com.example.schollink.model.Turma;
import com.example.schollink.model.TurmaDisciplina;
import com.example.schollink.model.User;
import com.example.schollink.model.UserRole;
import com.example.schollink.repository.AlunoRepository;
import com.example.schollink.repository.HistoricoAulaRepository;
import com.example.schollink.repository.HorarioAulaRepository;
import com.example.schollink.repository.PresencaRepository;
import com.example.schollink.repository.TurmaDisciplinaRepository;
import com.example.schollink.repository.UserRepository;

@Service
public class AlunoService {
    @Autowired
    private PasswordService passwordService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private TurmaDisciplinaRepository turmaDisciplinaRepository;
    @Autowired
    private HorarioAulaRepository horarioAulaRepository;
    @Autowired
    private PresencaRepository presencaRepository; 
    @Autowired
    private HistoricoAulaRepository historicoAulaRepository;

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
                    StatusMatricula.valueOf(alunoDto.getStatusMatricula().trim().toUpperCase()));
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

        if (aluno.getStatusMatricula().equals(StatusMatricula.ATIVA)) {
            aluno.setStatusMatricula(StatusMatricula.INATIVA);
            alunoRepository.save(aluno);
        }
    }

    public Aluno verAluno(Long id) {
        Aluno aluno = alunoRepository.findByUserId(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        return aluno;
    }

    public List<AlunoParaTurmaDto> buscarTodos() {
        List<Aluno> alunos = alunoRepository.findAll();
        return alunos.stream()
                .map(a -> new AlunoParaTurmaDto(a.getIdAluno(), a.getUser().getNome(), a.getTurma() != null))
                .collect(Collectors.toList());
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

    public List<DisciplinaProfessorDto> buscarDisciplinas(Long idUser) {
        Optional<Aluno> alunoOpt = alunoRepository.findByUserId(idUser);
        List<TurmaDisciplina> turmasDisciplinas = new ArrayList<>();
        List<DisciplinaProfessorDto> disciplinasProfessoresDtos = new ArrayList<>();
        if (alunoOpt.isPresent()) {
            Aluno aluno = alunoOpt.get();
            Turma turma = aluno.getTurma();
            turmasDisciplinas = turmaDisciplinaRepository.findByTurma(turma);
            if (!turmasDisciplinas.isEmpty()) {
                for (TurmaDisciplina turmaDisciplina : turmasDisciplinas) {
                    DisciplinaProfessorDto disciplinaProfessorDto = new DisciplinaProfessorDto();
                    disciplinaProfessorDto.setIdDisciplina(turmaDisciplina.getDisciplina().getId());
                    disciplinaProfessorDto.setNomeDisciplina(turmaDisciplina.getDisciplina().getNome());
                    disciplinaProfessorDto.setIdProfessor(turmaDisciplina.getProfessor().getId());
                    disciplinaProfessorDto.setNomeProfessor(turmaDisciplina.getProfessor().getUser().getNome());
                    disciplinaProfessorDto.setIdTurmaDisciplina(turmaDisciplina.getId());
                    disciplinasProfessoresDtos.add(disciplinaProfessorDto);
                }
            }
        }
        return disciplinasProfessoresDtos;
    }

    public PresencasAlunoDto buscarPresencas(Long idUser, Long idTurmaDisciplina) {
        Optional<Aluno> alunoOpt = alunoRepository.findByUserId(idUser);
        Optional<TurmaDisciplina> turmaDisciplinaOpt = turmaDisciplinaRepository.findById(idTurmaDisciplina);
        if (!alunoOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Aluno não encontrado para o usuário ID: " + idUser);
        }
        if (!turmaDisciplinaOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "TurmaDisciplina não encontrada para o ID: " + idTurmaDisciplina);
        }
        Aluno aluno = alunoOpt.get();
        TurmaDisciplina turmaDisciplina = turmaDisciplinaOpt.get();
        if (!turmaDisciplina.getTurma().equals(aluno.getTurma())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Aluno não participa dessa turma");
        }
        List<HorarioAula> horariosAulas = horarioAulaRepository.findByTurmaDisciplina(turmaDisciplina);
        int totalAulas = horariosAulas.size();
        ;
        int aulasPresente = 0;
        for (HorarioAula horarioAula : horariosAulas) {
            List<Presenca> presencas = horarioAula.getPresencas();
            for (Presenca presenca : presencas) {
                if (presenca.getAluno().equals(aluno) && Boolean.TRUE.equals(presenca.getPresente())) {
                    aulasPresente += 1;
                }
            }
        }
        PresencasAlunoDto presencasAlunoDto = new PresencasAlunoDto();
        presencasAlunoDto.setId(turmaDisciplina.getId());
        presencasAlunoDto.setNomeDisciplina(turmaDisciplina.getDisciplina().getNome());
        presencasAlunoDto.setTotalAulas(totalAulas);
        presencasAlunoDto.setPresencas(aulasPresente);

        return presencasAlunoDto;
    }

    public List<AulaRetornoDto> buscarAulasDia(DataDto dto) {

        Optional<TurmaDisciplina> turmaDisciplinaOpt = turmaDisciplinaRepository.findById(dto.getIdTurmaDisciplina());
        if (dto.getDia() == null || dto.getDia().isEmpty() || turmaDisciplinaOpt.isEmpty()) {
            throw new IllegalArgumentException("O campo 'dia' não pode ser nulo ou vazio");
        }
        LocalDate data = LocalDate.parse(dto.getDia());
        TurmaDisciplina turmaDisciplina = turmaDisciplinaOpt.get();

        List<HorarioAula> horarioAulas = horarioAulaRepository.findByDataAndTurmaDisciplina(data, turmaDisciplina);

        if (horarioAulas == null || horarioAulas.isEmpty()) {
            return new ArrayList<>();
        }

        List<AulaRetornoDto> aulasRetornoDtos = horarioAulas.stream().map(horarioAula -> {
            AulaRetornoDto aulaRetorno = new AulaRetornoDto();
            TurmaDisciplina td = horarioAula.getTurmaDisciplina();
            aulaRetorno.setIdDisciplina(td.getDisciplina().getId());
            aulaRetorno.setNomeDisciplina(td.getDisciplina().getNome());
            aulaRetorno.setIdHorarioAula(horarioAula.getId());
            aulaRetorno.setIdTurmaDisciplina(td.getId());
            aulaRetorno.setHorarioInicio(horarioAula.getHoraInicio());
            aulaRetorno.setHorarioTermino(horarioAula.getHoraFim());
            return aulaRetorno;
        }).collect(Collectors.toList());

        return aulasRetornoDtos;
    }
}
