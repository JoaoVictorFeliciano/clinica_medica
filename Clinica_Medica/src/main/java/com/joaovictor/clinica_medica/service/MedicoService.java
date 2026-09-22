package com.joaovictor.clinica_medica.service;

import com.joaovictor.clinica_medica.model.Medico;
import com.joaovictor.clinica_medica.repository.MedicoRepository;

import java.util.List;

public class MedicoService {

    private final MedicoRepository medicoRepository;

    public MedicoService(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    public void cadastrar(Medico medico) {

        if (medico.getCrm() == null || medico.getCrm().isBlank()) {
            throw new IllegalArgumentException("CRM é obrigatório");
        }

        if (medicoRepository.buscarPorCrm(medico.getCrm()) != null) {
            throw new IllegalArgumentException("CRM já cadastrado");
        }

        if (medico.getEspecialidade() == null ||
                medico.getEspecialidade().isBlank()) {
            throw new IllegalArgumentException(
                    "Especialidade é obrigatória"
            );
        }

        medicoRepository.salvar(medico);
    }

    public Medico buscarPorId(Long id) {
        return medicoRepository.buscarPorId(id);
    }

    public List<Medico> buscarTodos() {
        return medicoRepository.buscarTodos();
    }

    public void atualizar(Medico medico) {

        if (medico.getCrm() == null || medico.getCrm().isBlank()) {
            throw new IllegalArgumentException("CRM é obrigatório");
        }

        if (medico.getEspecialidade() == null ||
                medico.getEspecialidade().isBlank()) {
            throw new IllegalArgumentException(
                    "Especialidade é obrigatória"
            );
        }

        Medico medicoExistente = medicoRepository.buscarPorCrm(medico.getCrm());

        if (medicoExistente != null &&
                !medicoExistente.getId().equals(medico.getId())) {
            throw new IllegalArgumentException("CRM já cadastrado");
        }

        medicoRepository.atualizar(medico);
    }

    public void excluir(Long id) {
        medicoRepository.excluir(id);
    }
}