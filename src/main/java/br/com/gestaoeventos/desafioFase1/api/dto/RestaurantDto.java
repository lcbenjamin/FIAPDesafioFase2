package br.com.gestaoeventos.desafioFase1.api.dto;

import br.com.gestaoeventos.desafioFase1.domain.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RestaurantDto {

    @NotBlank
    public String nome;

    @NotNull
    public Address endereco;

    @NotBlank
    public String tipoCozinha;

    @NotBlank
    public String horarioFuncionamento;

    @NotNull
    public Long donoId;
}

