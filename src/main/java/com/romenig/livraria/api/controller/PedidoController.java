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

        @PostMapping
        public Pedido salvarPedido(@Valid @RequestBody Pedido pedido){
            return pedidoService.salvarPedido(pedido);
        }
}
