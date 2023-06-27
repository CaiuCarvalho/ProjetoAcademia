package com.caio.modelos;

import java.util.List;

import javax.persistence.*;

@Entity
public class Treino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String objetivo;

    @OneToMany(mappedBy = "treino", fetch = FetchType.EAGER)
    private List<Aluno> listaAlunosTreino;

    // construtor vazio
    public Treino() {
    }

    public Treino(String objetivo) {
        this.objetivo = objetivo;
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public List<Aluno> getListaAlunosTreino() {
        return listaAlunosTreino;
    }

    public void setListaAlunosTreino(List<Aluno> listaAlunosTreino) {
        this.listaAlunosTreino = listaAlunosTreino;
    }

    @Override
    public String toString() {
        return "Treino{" +
                "id=" + id +
                ", objetivo='" + objetivo + '\'' +
                ", listaAlunosTreino=" + listaAlunosTreino +
                '}';
    }

}
