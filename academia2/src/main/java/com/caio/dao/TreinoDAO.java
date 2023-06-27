package com.caio.dao;

import com.caio.modelos.*;

import javax.persistence.*;
import java.util.List;

public class TreinoDAO {
    private EntityManagerFactory emf;

    public TreinoDAO() {
        emf = Persistence.createEntityManagerFactory("my-persistence-unit");
    }

    public void salvarTreino(Treino treino) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(treino);
        em.getTransaction().commit();
        em.close();
    }

    public Treino buscarTreinoPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        Treino treino = em.find(Treino.class, id);
        em.close();
        return treino;
    }

    public List<Treino> buscarTodosTreinos() {
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT t FROM Treino t");
        List<Treino> treinos = query.getResultList();
        em.close();
        return treinos;
    }

    public void atualizarTreino(Treino treino) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(treino);
        em.getTransaction().commit();
        em.close();
    }

    public void excluirTreino(Treino treino) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        treino = em.merge(treino);
        em.remove(treino);
        em.getTransaction().commit();
        em.close();
    }

    public void excluirTodosTreinos(Treino treino) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Treino").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
