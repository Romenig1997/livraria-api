package com.romenig.livraria.api.service;

import com.romenig.livraria.api.model.ItemPedido;
import com.romenig.livraria.api.model.Livro;
import com.romenig.livraria.api.model.Pedido;
import com.romenig.livraria.api.repository.LivroRepository;
import com.romenig.livraria.api.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private LivroRepository livroRepository;

    // Retorna todos os pedidos
    public List<Pedido> todosPedidos() {
        return pedidoRepository.findAll();
    }

    // Salva um pedido com valor total calculado e itens vinculados
    public Pedido salvarPedido(Pedido pedido) {
        double total = 0.0;

        for (ItemPedido item : pedido.getItens()) {
            // Buscar o livro completo no banco
            Livro livroDoBanco = livroRepository.findById(item.getLivro().getId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Livro não encontrado: " + item.getLivro().getId()));

            // Atualiza os dados do item
            item.setLivro(livroDoBanco);       // livro completo
            item.setValor(livroDoBanco.getValor()); // valor unitário
            item.setPedido(pedido);             // vincula o item ao pedido

            // Calcula o subtotal deste item
            total += item.getValor() * item.getQuantidade();
        }

        // Define o valor total do pedido
        pedido.setValorTotal(total);

        // Salva o pedido com todos os itens
        return pedidoRepository.save(pedido);
    }
}
