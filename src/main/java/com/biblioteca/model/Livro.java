package com.biblioteca.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Livro {
    @Id
    @GeneratedValue(strategy  = GenerationType.IDENTITY)
    @Schema(example = "1", description = "Identificador único do livro")
    private  Long id;

    @Schema(example = "Ruínas de Minaster", description = "Título do livro")
    private String titulo;

    @Schema(example = "Raianne Viana", description = "Autor(es) do livro")
    private String autor;

    @Schema(example = "true", description = "O livro está emprestado")
    private boolean emprestado;
}