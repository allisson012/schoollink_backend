package com.example.schollink.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.schollink.Dto.AlunoDto;
import com.example.schollink.model.Aluno;
import com.example.schollink.model.Endereco;
import com.example.schollink.model.StatusMatricula;
import com.example.schollink.model.User;
import com.example.schollink.model.UserRole;
import com.example.schollink.service.AlunoService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/aluno")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @PostMapping("/cadastrar")
    public ResponseEntity<Map<String, String>> CadastrarAluno(@RequestBody AlunoDto alunoDto) {
        User user = new User();
        user.setNome(alunoDto.getUserDto().getNome());
        user.setEmail(alunoDto.getUserDto().getEmail());
        user.setCpf(alunoDto.getUserDto().getCpf());
        user.setDataNascimento(alunoDto.getUserDto().getDataNascimento());
        user.setGenero(alunoDto.getUserDto().getGenero());     
        user.setTelefone(alunoDto.getUserDto().getTelefone());
        if (user.getEndereco() == null) {
            user.setEndereco(new Endereco());
        }
        user.getEndereco().setCep(alunoDto.getEnderecoDto().getCep());
        user.getEndereco().setPais(alunoDto.getEnderecoDto().getPais());
        user.getEndereco().setEstado(alunoDto.getEnderecoDto().getEstado());
        user.getEndereco().setCidade(alunoDto.getEnderecoDto().getCidade());
        user.getEndereco().setRua(alunoDto.getEnderecoDto().getRua());
        user.getEndereco().setNumero(alunoDto.getEnderecoDto().getNumero());
        Aluno aluno = new Aluno();
        aluno.setMatricula(alunoDto.getMatricula());
        aluno.setDataMatricula(alunoDto.getDataMatricula());
        aluno.setStatusMatricula(StatusMatricula.valueOf(alunoDto.getStatusMatricula()));
        aluno.setNomeResponsavel(alunoDto.getNomeResponsavel());
        aluno.setTelefoneResponsavel(alunoDto.getTelefoneResponsavel());  
        
        alunoService.cadastrarAluno(user, aluno, alunoDto.getUserDto().getSenha());         
        Map<String, String> response = new HashMap<>();
        response.put("mensagem", "Aluno Cadastrado com sucesso");
        return ResponseEntity.ok(response);
    }

    // cadastrar usando userRole precisa de alguns ajustes no front para funcionar
    @PostMapping("/cadastrar/verificar")
    public ResponseEntity<Map<String, String>> CadastrarAlunoComVerificacao(@RequestBody AlunoDto alunoDto,
            HttpSession session) {
        User user = new User();
        user.setNome(alunoDto.getUserDto().getNome());
        user.setEmail(alunoDto.getUserDto().getEmail());
        Aluno aluno = new Aluno();
        UserRole userRole = (UserRole) session.getAttribute("UserRole");
        if (userRole != null && userRole.equals(UserRole.ADMIN)) {
            aluno.setMatricula(alunoDto.getMatricula());
            alunoService.cadastrarAluno(user, aluno, alunoDto.getUserDto().getSenha());
            Map<String, String> response = new HashMap<>();
            response.put("mensagem", "Aluno Cadastrado com sucesso");
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("mensagem", "Somente usuários do tipo ADMIN podem realizar essa ação");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @PutMapping("/editar")
    public ResponseEntity<?> editarAluno(@RequestBody AlunoDto alunoNovo, HttpSession session) {
        Long id = (Long) session.getAttribute("userId");

        if (id == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Usuario não logado"));
        }
        try {
            Aluno atualizado = alunoService.editarAluno(alunoNovo, id);
            return ResponseEntity.ok(Map.of("message", "Aluno atualizado com sucesso",
                    "id", String.valueOf(
                            atualizado.getIdAluno())));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        }
    }

    @PutMapping("/editar/verificar")
    public ResponseEntity<?> editarAlunoComVerificacao(@RequestBody AlunoDto alunoNovo, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        UserRole userRole = (UserRole) session.getAttribute("UserRole");

        if (userId == null || userRole == null || !userRole.equals(UserRole.ADMIN)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "Usuário não logado ou sem permissão"));
        }

        try {
            Aluno atualizado = alunoService.editarAluno(alunoNovo, alunoNovo.getIdAluno());
            return ResponseEntity.ok(Map.of("message", "Aluno atualizado com sucesso",
                    "id", String.valueOf(
                            atualizado.getIdAluno())));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<?> excluirAluno(HttpSession session, @RequestParam Long idAluno) {
        Long id = (Long) session.getAttribute("idUser");
        if (id == null || idAluno == null) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Usuário não logado ou ID do aluno não informado"));
        }
        try {
            alunoService.excluirAluno(idAluno);
            return ResponseEntity.ok().body(Map.of("message", "Aluno excluido com sucesso"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> verAluno(HttpSession session) {
        Long id = (Long) session.getAttribute("userId");
        if (id == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Usuário não logado ou ID do aluno não informado"));
        }
        try {
            Aluno aluno = alunoService.verAluno(id);
            return ResponseEntity.ok().body(Map.of("nome", aluno.getUser().getNome()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/buscarAluno/{idAluno}")
    public ResponseEntity<?> buscarAluno(HttpSession session, @PathVariable Long idAluno) {
        if (idAluno == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "ID do aluno não informado"));
        }
        try {
            Aluno aluno = alunoService.verAluno(idAluno);
            return ResponseEntity.ok().body(aluno);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<?> buscarAlunos(
        @RequestParam(required = false) String nome,
        @RequestParam(required = false) String matricula,
        @RequestParam(required = false) String email
    ) {
        Optional<List<Aluno>> aluno = alunoService.buscar(nome, matricula, email);
        
        if (aluno.isPresent()) {
            return ResponseEntity.ok(aluno.get());
        } else {
            return ResponseEntity.status(404).body("Aluno não encontrado");
        }
    }

}
