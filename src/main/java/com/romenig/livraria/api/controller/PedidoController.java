package com.romenig.livraria.api.controller;

import com.romenig.livraria.api.model.Pedido;
import com.romenig.livraria.api.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

        @Autowired
        private PedidoService pedidoService;

        @GetMapping
        public List<Pedido> listar(){
            return pedidoService.todosPedidos();
        }

        @GetMapping("/{id}")
        public Pedido buscarPorId(@PathVariable Long id){
            return pedidoService.buscarPorId(id);
        }

        @PostMapping
        public Pedido salvarPedido(@Valid @RequestBody Pedido pedido){
            return pedidoService.salvarPedido(pedido);
        }

        @PutMapping("/{id}/aprovar")
        public Pedido aprovarPedido(@PathVariable  Long id){
            return pedidoService.aprovarPedido(id);
        }

        @PutMapping("/{id}/enviar")
        public Pedido enviarPedido(@PathVariable Long id){
            return pedidoService.enviarPedido(id);
        }

        @PutMapping("/{id}/concluir")
        public Pedido concluirPedido(@PathVariable Long id){
            return  pedidoService.concluirPedido(id);
        }

        @PutMapping("/{id}/cancelar")
        public Pedido cancelarPedido(@PathVariable Long id){
            return pedidoService.cancelarPedido(id);
        }
}
