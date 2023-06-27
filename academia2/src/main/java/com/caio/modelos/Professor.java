package com.caio.modelos;

import java.util.List;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value = "professor")
public class Professor extends Pessoa {

    private String login, senha;

    @OneToMany(mappedBy = "professor", fetch = FetchType.EAGER)
    private List<Aluno> listaAluno;

    // construtor vazio
    public Professor() {
    }

    public Professor(String nome, int idade, String login, String senha) {
        super(nome, idade);
        this.login = login;
        this.senha = senha;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Aluno> getListaAluno() {
        return listaAluno;
    }

    public void setListaAluno(List<Aluno> listaAluno) {
        this.listaAluno = listaAluno;
    }

    @Override
    public String toString() {
        return "Professor{" +
                "login='" + login + '\'' +
                ", senha='" + senha + '\'' +
                ", listaAluno=" + listaAluno +
                ", id=" + id +
                ", nome='" + nome + '\'' +
                ", idade=" + idade +
                '}';
    }
}
