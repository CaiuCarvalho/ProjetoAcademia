package com.caio.dao;

import com.caio.modelos.Aluno;

import javax.persistence.*;
import java.util.List;

public class AlunoDAO {
    private EntityManagerFactory emf;

    public AlunoDAO() {
        emf = Persistence.createEntityManagerFactory("my-persistence-unit");
    }

    public void salvarAluno(Aluno aluno) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(aluno);
        em.getTransaction().commit();
        em.close();
    }

    public Aluno buscarAlunoPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        Aluno aluno = em.find(Aluno.class, id);
        em.close();
        return aluno;
    }

    public Aluno buscarAlunoPorNome(String nome) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Aluno> query = em.createQuery("SELECT a FROM Aluno a WHERE LOWER(a.nome) = LOWER(:nome)", Aluno.class);
        query.setParameter("nome", nome);
        List<Aluno> alunos = query.getResultList();
        em.close();
        return alunos.isEmpty() ? null : alunos.get(0);
    }


    public List<Aluno> buscarTodosAlunos() {
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT a FROM Aluno a");
        List<Aluno> alunos = query.getResultList();
        em.close();
        return alunos;
    }

    public void atualizarAluno(Aluno aluno) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(aluno);
        em.getTransaction().commit();
        em.close();
    }

    public void excluirAluno(Aluno aluno) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        aluno = em.merge(aluno);
        em.remove(aluno);
        em.getTransaction().commit();
        em.close();
    }

    public void excluirTodosAlunos() {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Aluno").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

}
