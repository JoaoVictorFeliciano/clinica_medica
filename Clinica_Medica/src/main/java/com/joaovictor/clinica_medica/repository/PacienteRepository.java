package com.joaovictor.clinica_medica.repository;

import com.joaovictor.clinica_medica.model.Paciente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class PacienteRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void salvar(Paciente paciente) {
        entityManager.persist(paciente);
    }

    public Paciente buscarPorId(Long id) {
        return entityManager.find(Paciente.class, id);
    }

    public List<Paciente> buscarTodos() {
        return entityManager
                .createQuery("SELECT p FROM Paciente p", Paciente.class)
                .getResultList();
    }

    @Transactional
    public void atualizar(Paciente paciente) {
        entityManager.merge(paciente);
    }

    @Transactional
    public void excluir(Long id) {
        Paciente paciente = buscarPorId(id);

        if (paciente != null) {
            entityManager.remove(paciente);
        }
    }
}