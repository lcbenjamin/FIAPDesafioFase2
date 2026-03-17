package br.com.gestaoeventos.desafioFase1.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;

@Entity
@Table(name = "usuarios", uniqueConstraints = {
        @UniqueConstraint(name = "uk_usuarios_email", columnNames = {"email"})
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String login;

    @NotBlank
    private String senha;

    private OffsetDateTime dataUltimaAlteracao;

    @Embedded
    @NotNull
    private Address endereco;

    @Enumerated(EnumType.STRING)
    @NotNull
    private UserType tipo;

    @PrePersist
    @PreUpdate
    public void atualizarDataUltimaAlteracao() {
        this.dataUltimaAlteracao = OffsetDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public OffsetDateTime getDataUltimaAlteracao() { return dataUltimaAlteracao; }
    public void setDataUltimaAlteracao(OffsetDateTime dataUltimaAlteracao) { this.dataUltimaAlteracao = dataUltimaAlteracao; }
    public Address getEndereco() { return endereco; }
    public void setEndereco(Address endereco) { this.endereco = endereco; }
    public UserType getTipo() { return tipo; }
    public void setTipo(UserType tipo) { this.tipo = tipo; }
}

