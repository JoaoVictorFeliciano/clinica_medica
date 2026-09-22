package com.joaovictor.clinica_medica.repository;

import com.joaovictor.clinica_medica.model.Medico;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public class MedicoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void salvar(Medico medico){
        entityManager.persist(medico);
    }

    public Medico buscarPorId(Long id){
        return entityManager.find(Medico.class, id);
    }

    public List<Medico> buscarTodos(){
        return entityManager.createQuery("SELECT m FROM Medico m", Medico.class).getResultList();
    }

    @Transactional
    public void atualizar(Medico medico){
        entityManager.merge(medico);
    }

    @Transactional
    public void excluir(Long id){
        Medico medico = buscarPorId(id);

        if (medico != null){
            entityManager.remove(medico);
        }
    }
}
