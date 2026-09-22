package com.joaovictor.clinica_medica.service;

import com.joaovictor.clinica_medica.dto.PacienteRequestDto;
import com.joaovictor.clinica_medica.dto.PacienteResponseDto;
import com.joaovictor.clinica_medica.model.Paciente;
import com.joaovictor.clinica_medica.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public PacienteResponseDto salvar(PacienteRequestDto dto) {

        if (dto.getCpf() == null || dto.getCpf().isBlank()) {
            throw new IllegalArgumentException("CPF é obrigatório");
        }

        if (pacienteRepository.buscarPorCpf(dto.getCpf()) != null) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }

        if (dto.getDataNascimento() != null &&
                dto.getDataNascimento().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "Data de nascimento não pode ser futura"
            );
        }

        Paciente paciente = new Paciente();

        paciente.setNome(dto.getNome());
        paciente.setCpf(dto.getCpf());
        paciente.setDataNascimento(dto.getDataNascimento());
        paciente.setTelefone(dto.getTelefone());

        pacienteRepository.salvar(paciente);

        return new PacienteResponseDto(
                paciente.getId(),
                paciente.getNome(),
                paciente.getCpf(),
                paciente.getDataNascimento(),
                paciente.getTelefone()
        );
    }

    public PacienteResponseDto buscarPorId(Long id) {

        Paciente paciente = pacienteRepository.buscarPorId(id);

        if (paciente == null) {
            return null;
        }

        return new PacienteResponseDto(
                paciente.getId(),
                paciente.getNome(),
                paciente.getCpf(),
                paciente.getDataNascimento(),
                paciente.getTelefone()
        );
    }

    public List<PacienteResponseDto> buscarTodos() {

        return pacienteRepository.buscarTodos()
                .stream()
                .map(paciente -> new PacienteResponseDto(
                        paciente.getId(),
                        paciente.getNome(),
                        paciente.getCpf(),
                        paciente.getDataNascimento(),
                        paciente.getTelefone()
                ))
                .toList();
    }

    public PacienteResponseDto atualizar(Long id, PacienteRequestDto dto) {

        Paciente pacienteExistente =
                pacienteRepository.buscarPorId(id);

        if (pacienteExistente == null) {
            throw new IllegalArgumentException("Paciente não encontrado");
        }

        if (dto.getCpf() == null || dto.getCpf().isBlank()) {
            throw new IllegalArgumentException("CPF é obrigatório");
        }

        Paciente pacienteComCpf =
                pacienteRepository.buscarPorCpf(dto.getCpf());

        if (pacienteComCpf != null &&
                !pacienteComCpf.getId().equals(id)) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }

        if (dto.getDataNascimento() != null &&
                dto.getDataNascimento().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "Data de nascimento não pode ser futura"
            );
        }

        pacienteExistente.setNome(dto.getNome());
        pacienteExistente.setCpf(dto.getCpf());
        pacienteExistente.setDataNascimento(dto.getDataNascimento());
        pacienteExistente.setTelefone(dto.getTelefone());

        pacienteRepository.atualizar(pacienteExistente);

        return new PacienteResponseDto(
                pacienteExistente.getId(),
                pacienteExistente.getNome(),
                pacienteExistente.getCpf(),
                pacienteExistente.getDataNascimento(),
                pacienteExistente.getTelefone()
        );
    }

    public void excluir(Long id) {

        Paciente paciente = pacienteRepository.buscarPorId(id);

        if (paciente == null) {
            throw new IllegalArgumentException("Paciente não encontrado");
        }

        pacienteRepository.excluir(id);
    }
}