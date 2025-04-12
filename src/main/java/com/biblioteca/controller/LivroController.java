package com.biblioteca.controller;

import com.biblioteca.model.Livro;
import com.biblioteca.repository.LivroRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/livros")
public class LivroController {
    private final LivroRepository repository;

    public LivroController(LivroRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{id}")
    public EntityModel<Livro> buscarPorId(@PathVariable Long id) {
        Livro livro = repository.findById(id).orElseThrow();

        return EntityModel.of(livro,
                linkTo(methodOn(LivroController.class).emprestarLivro(id)).withRel("emprestar"),
                linkTo(methodOn(LivroController.class).listarTodos()).withRel("todos-livros")
        );
    }

    @Operation(summary = "Emprestar um livro", description = "Marca um livro como emprestado e retorna links HATEOAS")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livro emprestado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    @PostMapping("/{id}/emprestar")
    public EntityModel<Livro> emprestarLivro(@PathVariable Long id) {
        Livro livro = repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado!")
        );

        if(livro.isEmprestado()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Livro já está emprestado!");
        }

        livro.setEmprestado(true);
        repository.save(livro);

        return EntityModel.of(livro,
                linkTo(methodOn(LivroController.class).buscarPorId(id)).withSelfRel(),
                linkTo(methodOn(LivroController.class).devolverLivro(id)).withRel("devolver"),
                linkTo(methodOn(LivroController.class).listarTodos()).withRel("todos-livros")
        );
    }

    @Operation(summary = "Devolver um livro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livro devolvido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Não há devolução pendente"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    @PostMapping("/{id}/devolver")
    public EntityModel<Livro> devolverLivro(@PathVariable Long id) {
        Livro livro = repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado!")
        );

        if(!livro.isEmprestado()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não há devolução pendente");
        }

        livro.setEmprestado(false);
        repository.save(livro);

        return EntityModel.of(livro,
                linkTo(methodOn(LivroController.class).buscarPorId(id)).withSelfRel()
        );
    }

    @Operation(summary = "Obter todos os livro", description = "Obtem todos os livros cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Livros não encontrados")
    })
    @GetMapping
    public List<Livro> listarTodos(){
        return repository.findAll();
    }

    @Operation(summary = "Cadastrar um livro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livro cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao cadastrar. Verifique os dados informados.")
    })
    @PostMapping
    public ResponseEntity<Livro> criarLivro(@RequestBody Livro livro) {
        Livro livroSalvo = repository.save(livro);
        return ResponseEntity.created(URI.create("/livros/" + livroSalvo.getId())).body(livroSalvo);
    }

}