package com.joaovictor.clinica_medica.service;

import com.joaovictor.clinica_medica.dto.ConsultaRequestDto;
import com.joaovictor.clinica_medica.dto.ConsultaResponseDto;
import com.joaovictor.clinica_medica.model.Consulta;
import com.joaovictor.clinica_medica.model.Medico;
import com.joaovictor.clinica_medica.model.Paciente;
import com.joaovictor.clinica_medica.repository.ConsultaRepository;
import com.joaovictor.clinica_medica.repository.MedicoRepository;
import com.joaovictor.clinica_medica.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;

    public ConsultaService(
            ConsultaRepository consultaRepository,
            MedicoRepository medicoRepository,
            PacienteRepository pacienteRepository) {

        this.consultaRepository = consultaRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
    }

    public ConsultaResponseDto salvar(ConsultaRequestDto dto) {

        if (dto.getMedicoId() == null) {
            throw new IllegalArgumentException("Médico é obrigatório");
        }

        if (dto.getPacienteId() == null) {
            throw new IllegalArgumentException("Paciente é obrigatório");
        }

        if (dto.getDataHora() == null) {
            throw new IllegalArgumentException("Data e hora são obrigatórias");
        }

        if (dto.getDataHora().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException(
                    "A consulta não pode ser agendada no passado"
            );
        }

        Medico medico = medicoRepository.buscarPorId(dto.getMedicoId());

        if (medico == null) {
            throw new IllegalArgumentException("Médico não encontrado");
        }

        Paciente paciente = pacienteRepository.buscarPorId(dto.getPacienteId());

        if (paciente == null) {
            throw new IllegalArgumentException("Paciente não encontrado");
        }

        if (consultaRepository.buscarPorMedicoEDataHora(
                medico.getId(),
                dto.getDataHora()) != null) {

            throw new IllegalArgumentException(
                    "O médico já possui uma consulta nesse horário"
            );
        }

        Consulta consulta = new Consulta();

        consulta.setDataHora(dto.getDataHora());
        consulta.setDescricao(dto.getDescricao());
        consulta.setMedico(medico);
        consulta.setPaciente(paciente);

        consultaRepository.salvar(consulta);

        return converterParaResponse(consulta);
    }

    public ConsultaResponseDto buscarPorId(Long id) {

        Consulta consulta = consultaRepository.buscarPorId(id);

        if (consulta == null) {
            return null;
        }

        return converterParaResponse(consulta);
    }

    public List<ConsultaResponseDto> buscarTodos() {

        return consultaRepository.buscarTodos()
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    public ConsultaResponseDto atualizar(
            Long id,
            ConsultaRequestDto dto) {

        Consulta consultaExistente =
                consultaRepository.buscarPorId(id);

        if (consultaExistente == null) {
            throw new IllegalArgumentException("Consulta não encontrada");
        }

        if (dto.getMedicoId() == null) {
            throw new IllegalArgumentException("Médico é obrigatório");
        }

        if (dto.getPacienteId() == null) {
            throw new IllegalArgumentException("Paciente é obrigatório");
        }

        if (dto.getDataHora() == null) {
            throw new IllegalArgumentException("Data e hora são obrigatórias");
        }

        if (dto.getDataHora().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException(
                    "A consulta não pode ser agendada no passado"
            );
        }

        Medico medico = medicoRepository.buscarPorId(dto.getMedicoId());

        if (medico == null) {
            throw new IllegalArgumentException("Médico não encontrado");
        }

        Paciente paciente = pacienteRepository.buscarPorId(dto.getPacienteId());

        if (paciente == null) {
            throw new IllegalArgumentException("Paciente não encontrado");
        }

        Consulta consultaNoHorario =
                consultaRepository.buscarPorMedicoEDataHora(
                        medico.getId(),
                        dto.getDataHora()
                );

        if (consultaNoHorario != null &&
                !consultaNoHorario.getId().equals(id)) {

            throw new IllegalArgumentException(
                    "O médico já possui uma consulta nesse horário"
            );
        }

        consultaExistente.setDataHora(dto.getDataHora());
        consultaExistente.setDescricao(dto.getDescricao());
        consultaExistente.setMedico(medico);
        consultaExistente.setPaciente(paciente);

        consultaRepository.atualizar(consultaExistente);

        return converterParaResponse(consultaExistente);
    }

    public void excluir(Long id) {

        Consulta consulta = consultaRepository.buscarPorId(id);

        if (consulta == null) {
            throw new IllegalArgumentException("Consulta não encontrada");
        }

        consultaRepository.excluir(id);
    }

    private ConsultaResponseDto converterParaResponse(Consulta consulta) {

        return new ConsultaResponseDto(
                consulta.getId(),
                consulta.getDataHora(),
                consulta.getDescricao(),
                consulta.getMedico().getId(),
                consulta.getPaciente().getId()
        );
    }
}