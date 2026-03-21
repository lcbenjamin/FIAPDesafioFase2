package br.com.gestaoeventos.desafioFase1.api.dto;

import br.com.gestaoeventos.desafioFase1.domain.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserUpdateDto {
    @NotBlank
    public String nome;
    @NotNull
    public Address endereco;
    @NotNull
    public Long tipoId;
}

