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

import jakarta.transaction.Transactional;

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
    @Autowired
    private EmailService emailService;

    @Transactional
    public boolean cadastrarAluno(AlunoDto alunoDto) {

        boolean valido = emailService.ValidateEmail(alunoDto.getUserDto().getEmail());
        if (!valido) {
            return false;
        }

        User user = new User();
        user.setNome(alunoDto.getUserDto().getNome());
        user.setEmail(alunoDto.getUserDto().getEmail());
        user.setCpf(alunoDto.getUserDto().getCpf());
        user.setDataNascimento(alunoDto.getUserDto().getDataNascimento());
        user.setGenero(alunoDto.getUserDto().getGenero());
        user.setTelefone(alunoDto.getUserDto().getTelefone());

        Endereco endereco = new Endereco();
        endereco.setCep(alunoDto.getEnderecoDto().getCep());
        endereco.setPais(alunoDto.getEnderecoDto().getPais());
        endereco.setEstado(alunoDto.getEnderecoDto().getEstado());
        endereco.setCidade(alunoDto.getEnderecoDto().getCidade());
        endereco.setRua(alunoDto.getEnderecoDto().getRua());
        endereco.setNumero(alunoDto.getEnderecoDto().getNumero());

        user.setEndereco(endereco);

        byte salt[] = passwordService.gerarSalt();
        byte hash[] = passwordService.gerarHash(alunoDto.getUserDto().getSenha(), salt);
        user.setSalt(salt);
        user.setHash(hash);
        user.setUserRole(UserRole.ALUNO);

        User userCreate = userRepository.save(user);

        Aluno aluno = new Aluno();
        aluno.setMatricula(alunoDto.getMatricula());
        aluno.setDataMatricula(alunoDto.getDataMatricula());
        aluno.setStatusMatricula(StatusMatricula.valueOf(alunoDto.getStatusMatricula()));
        aluno.setNomeResponsavel(alunoDto.getNomeResponsavel());
        aluno.setTelefoneResponsavel(alunoDto.getTelefoneResponsavel());
        aluno.setRfid(alunoDto.getRfid());
        aluno.setUser(userCreate);

        alunoRepository.save(aluno);

        return true;
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

    public Optional<List<AlunoDto>> buscar(String nome, String matricula, String email) {
        List<Aluno> alunos = new ArrayList<>();

        if (email != null && !email.isEmpty()) {
            alunos = alunoRepository.findByUserEmailContainingIgnoreCase(email);
        } else if (matricula != null && !matricula.isEmpty()) {
            alunos = alunoRepository.findByMatriculaContainingIgnoreCase(matricula);
        } else if (nome != null && !nome.isEmpty()) {
            alunos = alunoRepository.findByUserNomeContainingIgnoreCase(nome);
        }

        if (alunos.isEmpty()) {
            return Optional.empty();
        }

        List<AlunoDto> dtos = alunos.stream()
                .map(this::toDto)
                .toList();

        return Optional.of(dtos);
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

    private AlunoDto toDto(Aluno aluno) {
        AlunoDto dto = new AlunoDto();

        dto.setIdAluno(aluno.getIdAluno());
        dto.setMatricula(aluno.getMatricula());
        dto.setDataMatricula(aluno.getDataMatricula());
        dto.setStatusMatricula(aluno.getStatusMatricula().name());
        dto.setNomeResponsavel(aluno.getNomeResponsavel());
        dto.setTelefoneResponsavel(aluno.getTelefoneResponsavel());
        dto.setRfid(aluno.getRfid());

        UserDto userDto = new UserDto();
        userDto.setNome(aluno.getUser().getNome());
        userDto.setEmail(aluno.getUser().getEmail());
        userDto.setCpf(aluno.getUser().getCpf());
        userDto.setDataNascimento(aluno.getUser().getDataNascimento());
        userDto.setGenero(aluno.getUser().getGenero());
        userDto.setTelefone(aluno.getUser().getTelefone());
        dto.setUserDto(userDto);

        EnderecoDto end = new EnderecoDto();
        end.setCep(aluno.getUser().getEndereco().getCep());
        end.setPais(aluno.getUser().getEndereco().getPais());
        end.setEstado(aluno.getUser().getEndereco().getEstado());
        end.setCidade(aluno.getUser().getEndereco().getCidade());
        end.setRua(aluno.getUser().getEndereco().getRua());
        end.setNumero(aluno.getUser().getEndereco().getNumero());
        dto.setEnderecoDto(end);

        return dto;
    }

}
