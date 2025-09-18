package com.romenig.livraria.api.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.romenig.livraria.api.enums.Status;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status;


    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ItemPedido> itens = new ArrayList<>();

    private Double valorTotal;

    @OneToOne(cascade = CascadeType.ALL)
    private Cartao cartao;

    public Pedido() {}

    public Pedido(Long id, Status status, List<ItemPedido> itens, Double valorTotal, Cartao cartao) {
        this.id = id;
        this.status = status;
        this.itens = itens;
        this.valorTotal = valorTotal;
        this.cartao = cartao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Enum<Status> getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }


}
