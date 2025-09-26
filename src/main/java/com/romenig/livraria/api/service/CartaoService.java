package com.romenig.livraria.api.service;

import com.romenig.livraria.api.model.Cartao;
import com.romenig.livraria.api.repository.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.YearMonth;

@Service
public class CartaoService {

    @Autowired
    private CartaoRepository cartaoRepository;

    public Cartao salvarcartao(Cartao cartao){
        validarCartao(cartao.getValidade());

       return cartaoRepository.save(cartao);
    }


    public void validarCartao(YearMonth validade){
        YearMonth hoje = YearMonth.now();
        if (validade.isBefore(hoje)){
            throw new IllegalArgumentException("Cartão vencido: valiidade " + validade);
        }
    }
}
