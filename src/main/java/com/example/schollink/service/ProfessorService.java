package com.example.schollink.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.schollink.Dto.AlunoDto;
import com.example.schollink.Dto.AulaRetornoDto;
import com.example.schollink.Dto.ChamadaRequestDto;
import com.example.schollink.Dto.ProfessorParaTurmaDto;
import com.example.schollink.Dto.UserDto;
import com.example.schollink.model.Aluno;
import com.example.schollink.model.Disciplina;
import com.example.schollink.model.Funcionario;
import com.example.schollink.model.HorarioAula;
import com.example.schollink.model.Presenca;
import com.example.schollink.model.Professor;
import com.example.schollink.model.Turma;
import com.example.schollink.model.TurmaDisciplina;
import com.example.schollink.model.User;
import com.example.schollink.model.UserRole;
import com.example.schollink.repository.AlunoRepository;
import com.example.schollink.repository.HorarioAulaRepository;
import com.example.schollink.repository.PresencaRepository;
import com.example.schollink.repository.ProfessorRepository;
import com.example.schollink.repository.TurmaRepository;
import com.example.schollink.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class ProfessorService {
    @Autowired
    private PasswordService passwordService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProfessorRepository professorRepository;
    @Autowired
    private FuncionarioService funcionarioService;
    @Autowired
    private DisciplinaService disciplinaService;
    @Autowired
    private HorarioAulaRepository horarioAulaRepository;
    @Autowired
    private TurmaRepository turmaRepository;
    @Autowired
    private PresencaRepository presencaRepository;
    @Autowired
    private AlunoRepository alunoRepository;

    @Transactional
    public void cadastrarProfessor(User user, Professor professor, String senha) {
        byte[] salt = passwordService.gerarSalt();
        byte[] hash = passwordService.gerarHash(senha, salt);
        user.setSalt(salt);
        user.setHash(hash);
        user.setUserRole(UserRole.PROFESSOR);
        User savedUser = userRepository.save(user);
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(user.getNome());
        funcionario.setEmail(user.getEmail());
        funcionario.setGenero(user.getGenero());
        funcionario.setCpf(user.getCpf());
        funcionario.setTelefone(user.getTelefone());
        Funcionario funcionarioSalvo = funcionarioService.cadastrarFuncionario(funcionario);

        professor.setUser(savedUser);
        professor.setFuncionario(funcionarioSalvo);

        professorRepository.save(professor);
    }

    public Professor editarProfessor(Professor novo, Long id) {
        Optional<Professor> profOpt = professorRepository.findById(id);
        if (profOpt.isEmpty()) {
            throw new RuntimeException("Professor não encontrado");
        }

        Professor existente = profOpt.get();

        if (novo.getFormacaoAcademica() != null && !novo.getFormacaoAcademica().isBlank()) {
            existente.setFormacaoAcademica(novo.getFormacaoAcademica());
        }
        if (novo.getRegistroProfissional() != null && !novo.getRegistroProfissional().isBlank()) {
            existente.setRegistroProfissional(novo.getRegistroProfissional());
        }
        if (novo.getCargaHorariaSem() != 0) {
            existente.setCargaHorariaSem(novo.getCargaHorariaSem());
        }
        if (novo.getTurno() != null) {
            existente.setTurno(novo.getTurno());
        }
        if (novo.getSalario() != 0) {
            existente.setSalario(novo.getSalario());
        }
        if (novo.getDataContratacao() != null) {
            existente.setDataContratacao(novo.getDataContratacao());
        }
        if (novo.getUser() != null) {
            User userNovo = novo.getUser();
            User userExistente = existente.getUser();

            if (userNovo.getNome() != null && !userNovo.getNome().isBlank()) {
                userExistente.setNome(userNovo.getNome());
            }
            if (userNovo.getEmail() != null && !userNovo.getEmail().isBlank()) {
                userExistente.setEmail(userNovo.getEmail());
            }

            userRepository.save(userExistente);
        }
        return professorRepository.save(existente);
    }

    public void deletarProfessor(Long id) {
        Optional<Professor> profOpt = professorRepository.findById(id);
        if (profOpt.isEmpty()) {
            throw new RuntimeException("Professor não encontrado");
        }
        Professor existente = profOpt.get();
        professorRepository.delete(existente);
    }

    public List<AlunoDto> receberAlunosParaChamada(Long idProfessor, Long idHorarioAula) {
        HorarioAula horarioAula = horarioAulaRepository.findById(idHorarioAula)
                .orElseThrow(() -> new RuntimeException("Aula não encontrada"));

        // Pegando a turma através do TurmaDisciplina
        Turma turma = horarioAula.getTurmaDisciplina().getTurma();

        List<Aluno> alunos = turmaRepository.findById(turma.getId())
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"))
                .getAlunos();

        List<Presenca> presencas = presencaRepository.findByHorarioAulaId(idHorarioAula);
        Set<Long> idsPresentes = presencas.stream()
                .filter(Presenca::getPresente)
                .map(p -> p.getAluno().getIdAluno())
                .collect(Collectors.toSet());

        List<AlunoDto> alunosDto = alunos.stream().map(aluno -> {
            AlunoDto dto = new AlunoDto();
            dto.setMatricula(aluno.getMatricula());
            UserDto userDto = new UserDto();
            userDto.setUserId(aluno.getUser().getId());
            userDto.setNome(aluno.getUser().getNome());
            dto.setUserDto(userDto);
            dto.setPresenca(idsPresentes.contains(aluno.getIdAluno()));
            return dto;
        }).collect(Collectors.toList());

        return alunosDto.isEmpty() ? null : alunosDto;
    }

    public List<AulaRetornoDto> buscarAulasSemanaPeloId(Long idProfessor) {
        LocalDate hoje = LocalDate.now();
        LocalDate inicioSemana = hoje.with(DayOfWeek.MONDAY);
        LocalDate fimSemana = hoje.with(DayOfWeek.SUNDAY);

        // Agora buscamos pelas aulas do professor via TurmaDisciplina
        List<HorarioAula> horarioAulas = horarioAulaRepository.findByDataBetweenAndProfessorId(
                inicioSemana, fimSemana, idProfessor);

        if (horarioAulas == null || horarioAulas.isEmpty()) {
            return new ArrayList<>();
        }

        List<AulaRetornoDto> aulasRetornoDtos = horarioAulas.stream().map(horarioAula -> {
            AulaRetornoDto aulaRetorno = new AulaRetornoDto();
            TurmaDisciplina td = horarioAula.getTurmaDisciplina();
            aulaRetorno.setIdDisciplina(td.getDisciplina().getId());
            aulaRetorno.setNomeDisciplina(td.getDisciplina().getNome());
            aulaRetorno.setIdHorarioAula(horarioAula.getId());
            aulaRetorno.setIdProfessor(td.getProfessor().getId());
            aulaRetorno.setHorarioInicio(horarioAula.getHoraInicio());
            aulaRetorno.setHorarioTermino(horarioAula.getHoraFim());
            return aulaRetorno;
        }).collect(Collectors.toList());

        return aulasRetornoDtos;
    }

    public List<AulaRetornoDto> buscarAulasSemana(Long idUser) {
        Professor professor = professorRepository.findByUser_Id(idUser);
        if (professor == null) {
            return new ArrayList<>();
        }
        LocalDate hoje = LocalDate.now();
        LocalDate inicioSemana = hoje.with(DayOfWeek.MONDAY);
        LocalDate fimSemana = hoje.with(DayOfWeek.SUNDAY);

        // Agora buscamos pelas aulas do professor via TurmaDisciplina
        List<HorarioAula> horarioAulas = horarioAulaRepository.findByDataBetweenAndProfessorId(
                inicioSemana, fimSemana, professor.getId());

        if (horarioAulas == null || horarioAulas.isEmpty()) {
            return new ArrayList<>();
        }

        List<AulaRetornoDto> aulasRetornoDtos = horarioAulas.stream().map(horarioAula -> {
            AulaRetornoDto aulaRetorno = new AulaRetornoDto();
            TurmaDisciplina td = horarioAula.getTurmaDisciplina();
            aulaRetorno.setIdDisciplina(td.getDisciplina().getId());
            aulaRetorno.setNomeDisciplina(td.getDisciplina().getNome());
            aulaRetorno.setIdHorarioAula(horarioAula.getId());
            aulaRetorno.setIdProfessor(td.getProfessor().getId());
            aulaRetorno.setHorarioInicio(horarioAula.getHoraInicio());
            aulaRetorno.setHorarioTermino(horarioAula.getHoraFim());
            return aulaRetorno;
        }).collect(Collectors.toList());

        return aulasRetornoDtos;
    }

    public boolean realizarChamada(List<AlunoDto> alunos, Long idHorarioAula) {
        Optional<HorarioAula> horarioAulaOpt = horarioAulaRepository.findById(idHorarioAula);
        if (horarioAulaOpt.isEmpty()) {
            return false;
        }
        HorarioAula horarioAula = horarioAulaOpt.get();
        boolean peloMenosUmSalvo = false;
        for (AlunoDto alunoDto : alunos) {
            Aluno aluno = new Aluno();
            Optional<Aluno> alunoOpt = alunoRepository.findByUserId(alunoDto.getUserDto().getUserId());
            if (alunoOpt.isPresent()) {
                aluno = alunoOpt.get();
                Optional<Presenca> presencaOpt = presencaRepository.findByAlunoAndHorarioAula(aluno, horarioAula);

                Presenca presenca;
                if (presencaOpt.isPresent()) {
                    presenca = presencaOpt.get();
                } else {
                    presenca = new Presenca();
                    presenca.setAluno(aluno);
                    presenca.setHorarioAula(horarioAula);
                }
                presenca.setPresente(alunoDto.isPresenca());
                presencaRepository.save(presenca);
                peloMenosUmSalvo = true;
            }
        }
        return peloMenosUmSalvo;
    }

    public List<ProfessorParaTurmaDto> buscarTodos() {
        List<Professor> professores = professorRepository.findAll();
        return professores.stream()
                .map(p -> new ProfessorParaTurmaDto(p.getId(), p.getUser().getNome()))
                .collect(Collectors.toList());
    }

}
