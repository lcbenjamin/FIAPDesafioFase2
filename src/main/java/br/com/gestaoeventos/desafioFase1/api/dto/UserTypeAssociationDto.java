package br.com.gestaoeventos.desafioFase1.api.dto;

import jakarta.validation.constraints.NotNull;

public class UserTypeAssociationDto {
    @NotNull
    public Long tipoId;
}

