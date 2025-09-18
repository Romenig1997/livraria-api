package com.romenig.livraria.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "Livro")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do livro é obrigatório")
    @Column(name = "nome")
    private String nome;

    @NotBlank(message = "O nome do autor é obrigatório")
    @Column(name = "autor")
    private String autor;

    @NotNull(message = "A quantidade é obrigatória")
    @Positive(message = "A quantidade precisa ser positiva")
    @Column(name = "quantidade")
    private Integer quantidade;

    @Positive(message = "O valor do livro precisa ser positivo")
    @Column(name = "valor")
    private Double valor;

    public Livro() {
    }

    public Livro(Long id, String nome, String autor, Integer quantidade, Double valor) {
        this.id = id;
        this.nome = nome;
        this.autor = autor;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
