package com.joaovictor.clinica_medica.controller;

import com.joaovictor.clinica_medica.dto.PacienteRequestDto;
import com.joaovictor.clinica_medica.dto.PacienteResponseDto;
import com.joaovictor.clinica_medica.service.PacienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping
    public ResponseEntity<PacienteResponseDto> salvar(
            @RequestBody PacienteRequestDto dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(pacienteService.salvar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponseDto> buscarPorId(
            @PathVariable Long id) {

        PacienteResponseDto paciente = pacienteService.buscarPorId(id);

        if (paciente == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(paciente);
    }

    @GetMapping
    public ResponseEntity<List<PacienteResponseDto>> buscarTodos() {

        return ResponseEntity.ok(pacienteService.buscarTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponseDto> atualizar(
            @PathVariable Long id,
            @RequestBody PacienteRequestDto dto) {

        return ResponseEntity.ok(
                pacienteService.atualizar(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id) {

        pacienteService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}