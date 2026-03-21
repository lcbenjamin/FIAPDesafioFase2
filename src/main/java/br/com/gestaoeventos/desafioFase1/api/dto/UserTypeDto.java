package br.com.gestaoeventos.desafioFase1.api.dto;

import jakarta.validation.constraints.NotBlank;

public class UserTypeDto {
    @NotBlank
    public String nomeTipo;
}

