package Insper.usuario_pf.controller;

import Insper.usuario_pf.dto.CreateTarefaDTO;
import Insper.usuario_pf.dto.ReturnTarefaDTO;
import Insper.usuario_pf.service.TarefaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefa")
@RequiredArgsConstructor
public class TarefaController {
    private final TarefaService tarefaService;

    @PostMapping
    public ResponseEntity<ReturnTarefaDTO> createTarefa(
            @RequestBody @Valid CreateTarefaDTO dto,
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(tarefaService.createTarefa(dto, token));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTarefa(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String token) {
        tarefaService.deleteTarefa(id, token);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ReturnTarefaDTO>> getAllTarefas(
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(tarefaService.getAllTarefas(token));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReturnTarefaDTO> getTarefaById(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(tarefaService.getTarefaById(id, token));
    }

}

