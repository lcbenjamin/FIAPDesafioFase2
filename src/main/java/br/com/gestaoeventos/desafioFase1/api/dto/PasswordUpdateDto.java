package br.com.gestaoeventos.desafioFase1.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class PasswordUpdateDto {

    @NotBlank
    @JsonProperty("novaSenha")
    public String novaSenha;

}

