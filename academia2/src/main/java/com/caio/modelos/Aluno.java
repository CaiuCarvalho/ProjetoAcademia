package com.caio.modelos;

import java.util.List;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value = "aluno")
public class Aluno extends Pessoa{

    private int matricula;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @ManyToOne
    @JoinColumn(name = "treino_id")
    private Treino treino;

    //construtor vazio
    public Aluno(){
    }

    public Aluno(String nome, int idade, int matricula) {
        super(nome, idade);
        this.matricula = matricula;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Treino getTreino() {
        return treino;
    }

    public void setTreino(Treino treino) {
        this.treino = treino;
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "matricula=" + matricula +
                ", professor=" + professor +
                ", treino=" + treino +
                ", id=" + id +
                ", nome='" + nome + '\'' +
                ", idade=" + idade +
                '}';
    }

   
}
