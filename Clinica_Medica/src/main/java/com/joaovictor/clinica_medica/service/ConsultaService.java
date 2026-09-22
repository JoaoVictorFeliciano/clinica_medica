package com.joaovictor.clinica_medica.service;

import com.joaovictor.clinica_medica.model.Consulta;
import com.joaovictor.clinica_medica.repository.ConsultaRepository;
import com.joaovictor.clinica_medica.repository.MedicoRepository;
import com.joaovictor.clinica_medica.repository.PacienteRepository;

import java.time.LocalDateTime;
import java.util.List;

public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;

    public ConsultaService(
            ConsultaRepository consultaRepository,
            PacienteRepository pacienteRepository,
            MedicoRepository medicoRepository) {

        this.consultaRepository = consultaRepository;
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
    }

    public void cadastrar(Consulta consulta) {

        if (consulta.getPaciente() == null) {
            throw new IllegalArgumentException(
                    "Paciente é obrigatório"
            );
        }

        if (consulta.getMedico() == null) {
            throw new IllegalArgumentException(
                    "Médico é obrigatório"
            );
        }

        if (consulta.getDataHora() == null) {
            throw new IllegalArgumentException(
                    "Data e hora são obrigatórias"
            );
        }

        if (consulta.getDataHora().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException(
                    "A consulta não pode ser no passado"
            );
        }

        if (pacienteRepository.buscarPorId(
                consulta.getPaciente().getId()) == null) {

            throw new IllegalArgumentException(
                    "Paciente não encontrado"
            );
        }

        if (medicoRepository.buscarPorId(
                consulta.getMedico().getId()) == null) {

            throw new IllegalArgumentException(
                    "Médico não encontrado"
            );
        }

        if (consultaRepository.existeConsultaNoHorario(
                consulta.getMedico().getId(),
                consulta.getDataHora())) {

            throw new IllegalArgumentException(
                    "Médico já possui uma consulta nesse horário"
            );
        }

        consultaRepository.salvar(consulta);
    }

    public Consulta buscarPorId(Long id) {
        return consultaRepository.buscarPorId(id);
    }

    public List<Consulta> buscarTodos() {
        return consultaRepository.buscarTodos();
    }

    public void atualizar(Consulta consulta) {
        // validações semelhantes ao cadastro
    }

    public void excluir(Long id) {
        consultaRepository.excluir(id);
    }
}