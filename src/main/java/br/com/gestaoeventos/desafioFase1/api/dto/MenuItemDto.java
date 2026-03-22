package br.com.gestaoeventos.desafioFase1.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class MenuItemDto {

    @NotBlank
    public String nome;

    @NotBlank
    public String descricao;

    @NotNull
    @Positive
    public Double preco;

    @NotNull
    public Boolean apenasLocalizado;

    @NotBlank
    public String caminhoFoto;

    @NotNull
    public Long restauranteId;
}

