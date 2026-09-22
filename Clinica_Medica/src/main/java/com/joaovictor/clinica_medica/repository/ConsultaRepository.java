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

    public boolean existeConsultaNoHorario(Long medicoId, LocalDateTime dataHora) {

        Long quantidade = entityManager
                .createQuery(
                        "SELECT COUNT(c) FROM Consulta c " +
                                "WHERE c.medico.id = :medicoId " +
                                "AND c.dataHora = :dataHora",
                        Long.class
                )
                .setParameter("medicoId", medicoId)
                .setParameter("dataHora", dataHora)
                .getSingleResult();

        return quantidade > 0;
    }
    public Consulta buscarPorId(Long id) {
        return entityManager.find(Consulta.class, id);
    }

    public List<Consulta> buscarTodos() {
        return entityManager
                .createQuery("SELECT c FROM Consulta c", Consulta.class)
                .getResultList();
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
