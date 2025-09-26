package com.romenig.livraria.api.service;

import com.romenig.livraria.api.enums.Status;
import com.romenig.livraria.api.model.ItemPedido;
import com.romenig.livraria.api.model.Livro;
import com.romenig.livraria.api.model.Pedido;
import com.romenig.livraria.api.repository.LivroRepository;
import com.romenig.livraria.api.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public Pedido buscarPorId(Long id){
       return pedidoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido com o id "+id+" não existe."));

    }

    // Salva um pedido com valor total calculado e itens vinculados
    public Pedido salvarPedido(Pedido pedido) {
        double total = 0.0;

        for (ItemPedido item : pedido.getItens()) {
            // Buscar o livro completo no banco
            Livro livroDoBanco = livroRepository.findById(item.getLivro().getId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Livro não encontrado: " + item.getLivro().getId()));

            // Verifica se o livro tem disponivel ainda
            if(livroDoBanco.getQuantidade() <= 0){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantidade indisponivel " + livroDoBanco.getNome());
            }

            // Atualiza os dados do item
            item.setLivro(livroDoBanco);       // livro completo
            item.setValor(livroDoBanco.getValor()); // valor unitario
            item.setPedido(pedido);             // vincula o item ao pedido

            // Calcula o subtotal deste item
            total += item.getValor() * item.getQuantidade();
        }

        // Define o valor total do pedido
        pedido.setValorTotal(total);

        // Salva o pedido com todos os itens
        return pedidoRepository.save(pedido);
    }


    public Pedido aprovarPedido(Long idDoPedido){
        Pedido pedido = pedidoRepository.findById(idDoPedido).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Peido não encontrado"));

        if(pedido.getStatus() != Status.AGUARDANDO_APROVACAO){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pedido não pode ser aprovado");
        }

        // Atualizando o estoque a cada item pedido
        for (ItemPedido item : pedido.getItens()){
            Livro livro = item.getLivro();
            int quantidadeNova = livro.getQuantidade() - item.getQuantidade();

            if (quantidadeNova <= 0){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantidade insuficiente" + livro.getNome());
            }

            livro.setQuantidade(quantidadeNova);
            livroRepository.save(livro);
        }

        pedido.setStatus(Status.APROVADO_AGUARDANDO_ENVIO);
        return pedidoRepository.save(pedido);
    }

    //Enviar Pedido
    public Pedido enviarPedido(Long idDoPedido){
        Pedido pedido = pedidoRepository.findById(idDoPedido).orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pedido não encontrado"));

        //Testa caso o status seja diferente do desejado
        if (pedido.getStatus() != Status.AGUARDANDO_APROVACAO){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pedido não pode ser enviado");
        }

        //Salva o status e o pedido
        pedido.setStatus(Status.ENVIADO);
        return pedidoRepository.save(pedido);
    }

    //Conclui o pedido
    public Pedido concluirPedido(Long idDoPedido){
        //Busca o pedido pelo id
        Pedido pedido = pedidoRepository.findById(idDoPedido)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pedido não pode ser concluído"));

        //Testa caso o status seja diferente do desejado
        if(pedido.getStatus() != Status.AGUARDANDO_APROVACAO){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pedido não pode ser concluido");
        }

        pedido.setStatus(Status.COMPLETO);
        return  pedidoRepository.save(pedido);

    }


    //Cancela o pedido
    public Pedido cancelarPedido(Long idDoPedido){

        //Busca o pedido pelo id ou retorna o bad request
        Pedido pedido = pedidoRepository.findById(idDoPedido)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "PEdido não pode ser cancelado"));

        pedido.setStatus(Status.CANCELADO);
        return pedidoRepository.save(pedido);
    }

}
