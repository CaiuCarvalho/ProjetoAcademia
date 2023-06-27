package com.caio.dao;

import com.caio.modelos.*;

import javax.persistence.*;
import java.util.List;

public class UnidadeDAO {
    private EntityManagerFactory emf;

    public UnidadeDAO() {
        emf = Persistence.createEntityManagerFactory("my-persistence-unit");
    }

    public void salvarUnidade(Unidade unidade) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(unidade);
        em.getTransaction().commit();
        em.close();
    }

    public Unidade buscarUnidadePorId(Long id) {
        EntityManager em = emf.createEntityManager();
        Unidade unidade = em.find(Unidade.class, id);
        em.close();
        return unidade;
    }

    public Unidade buscarUnidadePorNome(String nome) {
        EntityManager em = emf.createEntityManager();
        String jpql = "SELECT u FROM Unidade u WHERE u.descricao = :nome";
        TypedQuery<Unidade> query = em.createQuery(jpql, Unidade.class);
        query.setParameter("nome", nome);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }    

    public List<Unidade> buscarTodosUnidades() {
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT u FROM Unidade u");
        List<Unidade> unidades = query.getResultList();
        em.close();
        return unidades;
    }

    public void atualizarUnidade(Unidade unidade) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(unidade);
        em.getTransaction().commit();
        em.close();
    }

    public void excluirUnidade(Unidade unidade) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        unidade = em.merge(unidade);
        em.remove(unidade);
        em.getTransaction().commit();
        em.close();
    }

    public void excluirTodosUnidades(Unidade unidade) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Unidade").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
