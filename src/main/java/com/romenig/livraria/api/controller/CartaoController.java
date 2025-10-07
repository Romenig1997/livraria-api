package com.romenig.livraria.api.controller;

import com.romenig.livraria.api.model.Cartao;
import com.romenig.livraria.api.repository.CartaoRepository;
import com.romenig.livraria.api.service.CartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.crypto.spec.PSource;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    @Autowired
    private CartaoService cartaoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cartao criarCartao(@RequestBody Cartao cartao){
     return cartaoService.salvarcartao(cartao);

    }
}
