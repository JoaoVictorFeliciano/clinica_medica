package com.joaovictor.clinica_medica.repository;

import com.joaovictor.clinica_medica.model.Consulta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ConsultaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void salvar(Consulta consulta) {
        entityManager.persist(consulta);
    }

    public Consulta buscarPorId(Long id) {
        return entityManager.find(Consulta.class, id);
    }

    public List<Consulta> buscarTodos() {
        return entityManager
                .createQuery("SELECT c FROM Consulta c", Consulta.class)
                .getResultList();
    }

    public Consulta buscarPorMedicoEDataHora(Long medicoId, LocalDateTime dataHora) {
        try {
            return entityManager
                    .createQuery(
                            "SELECT c FROM Consulta c " +
                                    "WHERE c.medico.id = :medicoId " +
                                    "AND c.dataHora = :dataHora",
                            Consulta.class
                    )
                    .setParameter("medicoId", medicoId)
                    .setParameter("dataHora", dataHora)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    public void atualizar(Consulta consulta) {
        entityManager.merge(consulta);
    }

    @Transactional
    public void excluir(Long id) {
        Consulta consulta = buscarPorId(id);

        if (consulta != null) {
            entityManager.remove(consulta);
        }
    }
}

