package com.example.schollink.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.schollink.Dto.AlunoDto;
import com.example.schollink.Dto.AulaRetornoDto;
import com.example.schollink.Dto.BuscarAulasDto;
import com.example.schollink.Dto.BuscarDisciplinasDto;
import com.example.schollink.Dto.ChamadaRequestDto;
import com.example.schollink.Dto.DataDto;
import com.example.schollink.Dto.ProfessorDto;
import com.example.schollink.Dto.ProfessorHorarioDto;
import com.example.schollink.Dto.ProfessorParaTurmaDto;
import com.example.schollink.model.Endereco;
import com.example.schollink.model.HistoricoAula;
import com.example.schollink.model.Professor;
import com.example.schollink.model.Turno;
import com.example.schollink.model.User;
import com.example.schollink.service.ProfessorService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/professor")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class ProfessorController {
    @Autowired
    private ProfessorService professorService;

    @PostMapping("/cadastrar")
    public ResponseEntity<Map<String, String>> cadastrarProfessor(@RequestBody ProfessorDto dto) {
        User user = new User();
        user.setNome(dto.getUserDto().getNome());
        user.setEmail(dto.getUserDto().getEmail());
        user.setCpf(dto.getUserDto().getCpf());
        user.setTelefone(dto.getUserDto().getTelefone());
        user.setDataNascimento(dto.getUserDto().getDataNascimento());
        user.setGenero(dto.getUserDto().getGenero());

        Professor professor = new Professor();
        professor.setFormacaoAcademica(dto.getFormacaoAcademica());
        professor.setRegistroProfissional(dto.getRegistroProfissional());
        professor.setDataContratacao(dto.getDataContratacao());
        professor.setCargaHorariaSem(dto.getCargaHorariaSem());
        professor.setSalario(dto.getSalario());
        professor.setTurno(Turno.valueOf(dto.getTurno().toUpperCase()));

        Endereco endereco = new Endereco();
        endereco.setCep(dto.getEnderecoDto().getCep());
        endereco.setPais(dto.getEnderecoDto().getPais());
        endereco.setEstado(dto.getEnderecoDto().getEstado());
        endereco.setCidade(dto.getEnderecoDto().getCidade());
        endereco.setRua(dto.getEnderecoDto().getRua());
        endereco.setNumero(dto.getEnderecoDto().getNumero());
        professor.setEndereco(endereco);

        professorService.cadastrarProfessor(user, professor, dto.getUserDto().getSenha());

        Map<String, String> response = new HashMap<>();
        response.put("mensagem", "Professor cadastrado com sucesso");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/editar")
    public ResponseEntity<?> editarProfessor(@RequestBody Professor novo, HttpSession session) {
        Long id = (Long) session.getAttribute("userId");
        if (id == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Usuário não logado"));

        try {
            Professor atualizado = professorService.editarProfessor(novo, id);
            return ResponseEntity
                    .ok(Map.of("message", "Professor atualizado", "id", String.valueOf(atualizado.getId())));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<?> excluirProfessor(HttpSession session, @RequestParam Long idProfessor) {
        Long id = (Long) session.getAttribute("userId");
        if (id == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Usuário não logado"));

        try {
            professorService.deletarProfessor(idProfessor);
            return ResponseEntity.ok(Map.of("message", "Professor excluído com sucesso"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        }
    }

    // tenho que mudar retorno para não retornar tudo de uma vez
    @PostMapping("/buscar/aulas/peloId")
    public ResponseEntity<?> buscarAulasSemanaPeloId(@RequestBody BuscarAulasDto dto) {
        Long idProfessor = dto.getIdProfessor();
        List<AulaRetornoDto> aulaRetornoDtos = professorService.buscarAulasSemanaPeloId(idProfessor);
        if (!aulaRetornoDtos.isEmpty()) {
            return ResponseEntity.ok(aulaRetornoDtos);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Erro ao buscar aulas ou lista vazia"));
        }
    }

    @PostMapping("/buscar/aulas/dia")
    public ResponseEntity<?> buscarAulasPeloDia(@RequestBody DataDto data, HttpSession session) {
        Long idUser = (Long) session.getAttribute("userId");
        if (idUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Usuário não autenticado"));
        }
        List<AulaRetornoDto> aulaRetornoDtos = professorService.buscarAulasDia(data);
        if (!aulaRetornoDtos.isEmpty()) {
            return ResponseEntity.ok(aulaRetornoDtos);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Erro ao buscar aulas ou lista vazia"));
        }
    }

    // semana
    @GetMapping("/buscar/aulas")
    public ResponseEntity<?> buscarAulasSemana(HttpSession session) {
        Long idUser = (Long) session.getAttribute("userId");
        if (idUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Usuário não autenticado"));
        }
        List<AulaRetornoDto> aulaRetornoDtos = professorService.buscarAulasSemana(idUser);
        if (!aulaRetornoDtos.isEmpty()) {
            return ResponseEntity.ok(aulaRetornoDtos);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Erro ao buscar aulas ou lista vazia"));
        }
    }

    // buscar aulas do dia

    @PostMapping("/buscar/chamada")
    public ResponseEntity<?> buscarAlunosChamada(@RequestBody ProfessorHorarioDto dto) {
        Long idProfessor = dto.getIdProfessor();
        Long idHorarioAula = dto.getIdHorarioAula();
        System.out.println("id do professor = " + idProfessor);
        System.out.println("id do horario aula = " + idHorarioAula);
        List<AlunoDto> alunosDtos = new ArrayList<AlunoDto>();
        alunosDtos = professorService.receberAlunosParaChamada(idProfessor, idHorarioAula);
        for (AlunoDto alunoDto : alunosDtos) {
            System.out.println(alunoDto.getUserDto().getNome());
            System.out.println(alunoDto.getMatricula());
        }
        if (alunosDtos != null && !alunosDtos.isEmpty()) {
            return ResponseEntity.ok(alunosDtos);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Erro ao buscar alunos ou lista vazia"));
        }
    }

    @PostMapping("/realizar/chamada")
    public ResponseEntity<?> realizarChamada(@RequestBody ChamadaRequestDto dto) {
        Long idHorarioAula = dto.getIdHorarioAula();
        List<AlunoDto> alunos = dto.getAlunos();
        HistoricoAula historicoAula = new HistoricoAula();

        boolean temTarefa = Boolean.TRUE.equals(dto.getTarefa());
        if (temTarefa) {
            historicoAula.setConteudoMinistrado(dto.getConteudoMinistrado());
            historicoAula.setDescricaoTarefa(dto.getDescricao());
            historicoAula.setTarefa(true);
            historicoAula.setResumoAula(dto.getResumoAula());
        }
        boolean chamada = professorService.realizarChamada(alunos, idHorarioAula, historicoAula);

        if (chamada) {
            return ResponseEntity.ok(Map.of("message", "Chamada realizada com sucesso"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Erro ao realizar chamada"));
        }
    }

    @GetMapping("/buscar-todos")
    public List<ProfessorParaTurmaDto> buscarTodos() {
        return professorService.buscarTodos();
    }

    @GetMapping("buscar/turmasDisciplinas")
    public ResponseEntity<List<BuscarDisciplinasDto>> buscarTurmasDisciplinas(HttpSession session) {
        Long idUser = (Long) session.getAttribute("idUser");
        List<BuscarDisciplinasDto> buscarDisciplinasDto = professorService.buscarTurmasDisciplinas(idUser);
        if (buscarDisciplinasDto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
        }
        return ResponseEntity.ok(buscarDisciplinasDto);
    }

}
