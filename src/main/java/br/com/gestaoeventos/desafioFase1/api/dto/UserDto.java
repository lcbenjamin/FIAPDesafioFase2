package br.com.gestaoeventos.desafioFase1.api.dto;

import br.com.gestaoeventos.desafioFase1.domain.Address;
import br.com.gestaoeventos.desafioFase1.domain.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserDto {
    @NotBlank
    public String nome;
    @Email @NotBlank
    public String email;
    @NotBlank
    public String login;
    @NotBlank
    public String senha;
    @NotNull
    public Address endereco;
    @NotNull
    public UserType tipo;
}

