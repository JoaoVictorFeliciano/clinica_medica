package com.joaovictor.clinica_medica.service;

import com.joaovictor.clinica_medica.dto.MedicoRequestDto;
import com.joaovictor.clinica_medica.dto.MedicoResponseDto;
import com.joaovictor.clinica_medica.model.Medico;
import com.joaovictor.clinica_medica.repository.MedicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicoService {

    private final MedicoRepository medicoRepository;

    public MedicoService(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    public MedicoResponseDto salvar(MedicoRequestDto dto) {

        if (dto.getCrm() == null || dto.getCrm().isBlank()) {
            throw new IllegalArgumentException("CRM é obrigatório");
        }

        if (dto.getEspecialidade() == null || dto.getEspecialidade().isBlank()) {
            throw new IllegalArgumentException("Especialidade é obrigatória");
        }

        if (medicoRepository.buscarPorCrm(dto.getCrm()) != null) {
            throw new IllegalArgumentException("CRM já cadastrado");
        }

        Medico medico = new Medico();

        medico.setNome(dto.getNome());
        medico.setCrm(dto.getCrm());
        medico.setEspecialidade(dto.getEspecialidade());

        medicoRepository.salvar(medico);

        return new MedicoResponseDto(
                medico.getId(),
                medico.getNome(),
                medico.getCrm(),
                medico.getEspecialidade()
        );
    }

    public MedicoResponseDto buscarPorId(Long id) {

        Medico medico = medicoRepository.buscarPorId(id);

        if (medico == null) {
            return null;
        }

        return new MedicoResponseDto(
                medico.getId(),
                medico.getNome(),
                medico.getCrm(),
                medico.getEspecialidade()
        );
    }

    public List<MedicoResponseDto> buscarTodos() {

        return medicoRepository.buscarTodos()
                .stream()
                .map(medico -> new MedicoResponseDto(
                        medico.getId(),
                        medico.getNome(),
                        medico.getCrm(),
                        medico.getEspecialidade()
                ))
                .toList();
    }

    public MedicoResponseDto atualizar(Long id, MedicoRequestDto dto) {

        Medico medicoExistente = medicoRepository.buscarPorId(id);

        if (medicoExistente == null) {
            throw new IllegalArgumentException("Médico não encontrado");
        }

        if (dto.getCrm() == null || dto.getCrm().isBlank()) {
            throw new IllegalArgumentException("CRM é obrigatório");
        }

        if (dto.getEspecialidade() == null || dto.getEspecialidade().isBlank()) {
            throw new IllegalArgumentException("Especialidade é obrigatória");
        }

        Medico medicoComCrm = medicoRepository.buscarPorCrm(dto.getCrm());

        if (medicoComCrm != null &&
                !medicoComCrm.getId().equals(id)) {
            throw new IllegalArgumentException("CRM já cadastrado");
        }

        medicoExistente.setNome(dto.getNome());
        medicoExistente.setCrm(dto.getCrm());
        medicoExistente.setEspecialidade(dto.getEspecialidade());

        medicoRepository.atualizar(medicoExistente);

        return new MedicoResponseDto(
                medicoExistente.getId(),
                medicoExistente.getNome(),
                medicoExistente.getCrm(),
                medicoExistente.getEspecialidade()
        );
    }

    public void excluir(Long id) {

        Medico medico = medicoRepository.buscarPorId(id);

        if (medico == null) {
            throw new IllegalArgumentException("Médico não encontrado");
        }

        medicoRepository.excluir(id);
    }
}