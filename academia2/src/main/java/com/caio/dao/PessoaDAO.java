package com.caio.dao;

import com.caio.modelos.Pessoa;

import javax.persistence.*;
import java.util.List;

public class PessoaDAO {
    private EntityManagerFactory emf;

    public PessoaDAO() {
        emf = Persistence.createEntityManagerFactory("my-persistence-unit");
    }

    public void salvarPessoa(Pessoa pessoa) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(pessoa);
        em.getTransaction().commit();
        em.close();
    }

    public Pessoa buscarPessoaPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        Pessoa pessoa = em.find(Pessoa.class, id);
        em.close();
        return pessoa;
    }

    public List<Pessoa> buscarTodasPessoas() {
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT p FROM Pessoa p");
        List<Pessoa> pessoas = query.getResultList();
        em.close();
        return pessoas;
    }

    public void atualizarPessoa(Pessoa pessoa) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(pessoa);
        em.getTransaction().commit();
        em.close();
    }

    public void excluirPessoa(Pessoa pessoa) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        pessoa = em.merge(pessoa);
        em.remove(pessoa);
        em.getTransaction().commit();
        em.close();
    }

    public void excluirTodasPessoas(Pessoa pessoa) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Pessoa").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void limparBancoDeDados() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Pessoa").executeUpdate();
            em.createQuery("DELETE FROM Unidade").executeUpdate();
            em.createQuery("DELETE FROM Treino").executeUpdate();
            // Adicionr outras entidades que deseja limpar, se necessário
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
