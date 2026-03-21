package br.com.gestaoeventos.desafioFase1.api.dto;

import br.com.gestaoeventos.desafioFase1.domain.Address;

public class RestaurantResponseDto {
    public Long id;
    public String nome;
    public Address endereco;
    public String tipoCozinha;
    public String horarioFuncionamento;
    public Long donoId;
}

