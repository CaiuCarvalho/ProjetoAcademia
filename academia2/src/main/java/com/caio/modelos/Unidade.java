    package com.caio.modelos;

    import java.util.List;

    import javax.persistence.*;

    @Entity
    public class Unidade {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id_unidade;

        private String descricao;

        @OneToMany(mappedBy = "unidade", fetch = FetchType.EAGER)
        private List<Pessoa> ListaPessoasUnidade;

        public Unidade() {
        }

        public Unidade(String descricao) {
            this.descricao = descricao;
        }

    public Long getId_unidade() {
        return id_unidade;
    }

    public void setId_unidade(Long id_unidade) {
        this.id_unidade = id_unidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Pessoa> getListaPessoasUnidade() {
        return ListaPessoasUnidade;
    }

    public void setListaPessoasUnidade(List<Pessoa> ListaPessoasUnidade) {
        this.ListaPessoasUnidade = ListaPessoasUnidade;
    }

    @Override
    public String toString() {
        return "Unidade{" +
                "id_unidade=" + id_unidade +
                ", descricao='" + descricao + '\'' +
                ", ListaPessoasUnidade=" + ListaPessoasUnidade +
                '}';
    }        
        
}
