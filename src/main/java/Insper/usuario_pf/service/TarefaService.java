package Insper.usuario_pf.service;

import Insper.usuario_pf.dto.CreateTarefaDTO;
import Insper.usuario_pf.dto.ReturnTarefaDTO;
import Insper.usuario_pf.dto.UsuarioResponseDTO;
import Insper.usuario_pf.model.Tarefa;
import Insper.usuario_pf.repository.TarefaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class TarefaService {
    private final TarefaRepository tarefaRepository;
    private final RestTemplate restTemplate;

    private UsuarioResponseDTO validarToken(String token) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<UsuarioResponseDTO> response = restTemplate.exchange(
                    "http://184.72.80.215/usuario/validate",
                    HttpMethod.GET,
                    entity,
                    UsuarioResponseDTO.class
            );

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }

            return response.getBody();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    public ReturnTarefaDTO createTarefa(CreateTarefaDTO dto, String token) {
        UsuarioResponseDTO usuario = validarToken(token);

        if (!"ADMIN".equals(usuario.getPapel())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Apenas administradores podem criar tarefas");
        }

        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo(dto.getTitulo());
        tarefa.setDescricao(dto.getDescricao());
        tarefa.setProjeto(dto.getProjeto());

        return ReturnTarefaDTO.fromEntity(tarefaRepository.save(tarefa));
    }

    public void deleteTarefa(Integer id, String token) {
        UsuarioResponseDTO usuario = validarToken(token);

        if (!"ADMIN".equals(usuario.getPapel())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Apenas administradores podem deletar tarefas");
        }

        if (!tarefaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada");
        }

        tarefaRepository.deleteById(id);
    }

    public List<ReturnTarefaDTO> getAllTarefas(String token) {
        validarToken(token); // Apenas valida o token, não precisa verificar papel

        return tarefaRepository.findAll().stream()
                .map(ReturnTarefaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public ReturnTarefaDTO getTarefaById(Integer id, String token) {
        validarToken(token); // Apenas valida o token, não precisa verificar papel

        return tarefaRepository.findById(id)
                .map(ReturnTarefaDTO::fromEntity)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada"));
    }
}
