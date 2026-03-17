package br.com.gestaoeventos.desafioFase1.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;

@Embeddable
public class Address {
    @NotBlank
    private String rua;
    @NotBlank
    private String numero;
    @NotBlank
    private String cidade;
    @NotBlank
    private String cep;

    public String getRua() { return rua; }
    public void setRua(String rua) { this.rua = rua; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }
    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }
}

