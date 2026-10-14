package com.joaovictor.clinica_medica.controller;

import com.joaovictor.clinica_medica.dto.ConsultaRequestDto;
import com.joaovictor.clinica_medica.dto.ConsultaResponseDto;
import com.joaovictor.clinica_medica.service.ConsultaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    private final ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @PostMapping
    public ResponseEntity<ConsultaResponseDto> salvar(
            @RequestBody ConsultaRequestDto dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(consultaService.salvar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaResponseDto> buscarPorId(
            @PathVariable Long id) {

        ConsultaResponseDto consulta =
                consultaService.buscarPorId(id);

        if (consulta == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(consulta);
    }

    @GetMapping
    public ResponseEntity<List<ConsultaResponseDto>> buscarTodos() {

        return ResponseEntity.ok(consultaService.buscarTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultaResponseDto> atualizar(
            @PathVariable Long id,
            @RequestBody ConsultaRequestDto dto) {

        return ResponseEntity.ok(
                consultaService.atualizar(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id) {

        consultaService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}