package com.romenig.livraria.api.service;

import com.romenig.livraria.api.model.Livro;
import com.romenig.livraria.api.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    public List<Livro> listarTodos(){
        return livroRepository.findAll();
    }

    public Livro buscarPorId(Long id){
       return livroRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Livro com id: " + id + (" não existe")));

    }

    public Livro adicionarLivro(Livro livro){
        return livroRepository.save(livro);
    }

    public void delatarPorId(Long id){
       Livro livro = livroRepository.findById(id)
               .orElseThrow(()->new RuntimeException("Livro não encontrado"));

       livroRepository.delete(livro);
    }
}
