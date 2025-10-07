package com.romenig.livraria.api.controller;

import com.romenig.livraria.api.model.Livro;
import com.romenig.livraria.api.repository.LivroRepository;
import com.romenig.livraria.api.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    //Endpoint para buscar todos os livros
    @GetMapping
    public List<Livro> buscar(){
        return livroService.listarTodos();
    }

    @GetMapping("/{id}")
    public Livro buscarLivroPorId(@PathVariable Long id){
        return livroService.buscarPorId(id);

    }

    //Endpoint para adicionar livro
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Livro adicionarLivro(@Valid @RequestBody Livro livro){
        return livroService.adicionarLivro(livro);
    }

    //Endpoint para deletar um livro por id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarLivro(@PathVariable Long id){
         livroService.delatarPorId(id);

         return ResponseEntity.ok("Livro delatado com sucesso");
    }
}
