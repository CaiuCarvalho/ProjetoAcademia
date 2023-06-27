package com.caio.dao;

import com.caio.modelos.Professor;

import javax.persistence.*;
import java.util.List;


public class ProfessorDAO {
    private EntityManagerFactory emf;

    public ProfessorDAO() {
        emf = Persistence.createEntityManagerFactory("my-persistence-unit");
    }

    public void salvarProfessor(Professor professor) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(professor);
        em.getTransaction().commit();
        em.close();
    }

    public Professor buscarProfessorPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        Professor professor = em.find(Professor.class, id);
        em.close();
        return professor;
    }

    public Professor buscarProfessorPorNome(String nome) {
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT p FROM Professor p WHERE p.nome = :nome");
        query.setParameter("nome", nome);
        Professor professor = null;
        try {
            professor = (Professor) query.getSingleResult();
        } catch (NoResultException e) {
            // Caso Professor não for encontrado
        } finally {
            em.close();
        }
        return professor;
    }

    public List<Professor> buscarTodosProfessores() {
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT p FROM Professor p");
        List<Professor> professores = query.getResultList();
        em.close();
        return professores;
    }

    public void atualizarProfessor(Professor professor) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(professor);
        em.getTransaction().commit();
        em.close();
    }

    public boolean validandoCredenciaisProfessor(String login, String senha) {
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT p FROM Professor p WHERE p.login = :login AND p.senha = :senha");
        query.setParameter("login", login);
        query.setParameter("senha", senha);
        List<Professor> resultados = query.getResultList();
        em.close();
        return !resultados.isEmpty();
    } 

    public void excluirProfessor(Professor professor) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        professor = em.merge(professor);
        em.remove(professor);
        em.getTransaction().commit();
        em.close();
    }

    public void excluirTodosProfessores() {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Professor").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
