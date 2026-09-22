package com.joaovictor.clinica_medica.service;

import com.joaovictor.clinica_medica.model.Paciente;
import com.joaovictor.clinica_medica.repository.PacienteRepository;

import java.time.LocalDate;
import java.util.List;

public class PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public void cadastrar(Paciente paciente) {

        if (paciente.getCpf() == null || paciente.getCpf().isBlank()) {
            throw new IllegalArgumentException("CPF é obrigatório");
        }

        if (pacienteRepository.buscarPorCpf(paciente.getCpf()) != null) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }

        if (paciente.getDataNascimento() != null &&
                paciente.getDataNascimento().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "Data de nascimento não pode ser futura"
            );
        }

        pacienteRepository.salvar(paciente);
    }

    public Paciente buscarPorId(Long id) {
        return pacienteRepository.buscarPorId(id);
    }

    public List<Paciente> buscarTodos() {
        return pacienteRepository.buscarTodos();
    }

    public void atualizar(Paciente paciente) {

        if (paciente.getCpf() == null || paciente.getCpf().isBlank()) {
            throw new IllegalArgumentException("CPF é obrigatório");
        }

        if (paciente.getDataNascimento() != null &&
                paciente.getDataNascimento().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "Data de nascimento não pode ser futura"
            );
        }

        Paciente pacienteExistente = pacienteRepository.buscarPorCpf(paciente.getCpf());

        if (pacienteExistente != null &&
                !pacienteExistente.getId().equals(paciente.getId())) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }

        pacienteRepository.atualizar(paciente);
    }

    public void excluir(Long id) {
        pacienteRepository.excluir(id);
    }
}