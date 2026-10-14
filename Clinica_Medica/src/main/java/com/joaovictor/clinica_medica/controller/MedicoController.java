package com.joaovictor.clinica_medica.controller;

import com.joaovictor.clinica_medica.dto.MedicoRequestDto;
import com.joaovictor.clinica_medica.dto.MedicoResponseDto;
import com.joaovictor.clinica_medica.service.MedicoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    private final MedicoService medicoService;

    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @PostMapping
    public ResponseEntity<MedicoResponseDto> salvar(
            @RequestBody MedicoRequestDto dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(medicoService.salvar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicoResponseDto> buscarPorId(
            @PathVariable Long id) {

        MedicoResponseDto medico = medicoService.buscarPorId(id);

        if (medico == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(medico);
    }

    @GetMapping
    public ResponseEntity<List<MedicoResponseDto>> buscarTodos() {

        return ResponseEntity.ok(medicoService.buscarTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicoResponseDto> atualizar(
            @PathVariable Long id,
            @RequestBody MedicoRequestDto dto) {

        return ResponseEntity.ok(
                medicoService.atualizar(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id) {

        medicoService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}