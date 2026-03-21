package br.com.gestaoeventos.desafioFase1.api.controller;

import br.com.gestaoeventos.desafioFase1.api.dto.PasswordUpdateDto;
import br.com.gestaoeventos.desafioFase1.api.dto.UserDto;
import br.com.gestaoeventos.desafioFase1.api.dto.UserTypeAssociationDto;
import br.com.gestaoeventos.desafioFase1.api.dto.UserUpdateDto;
import br.com.gestaoeventos.desafioFase1.api.mapper.UserMapper;
import br.com.gestaoeventos.desafioFase1.domain.User;
import br.com.gestaoeventos.desafioFase1.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Usuários")
@RestController
@RequestMapping("/api/v1/usuarios")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Cadastrar usuário")
    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody UserDto dto) {
        User user = UserMapper.toEntity(dto);
        User created = userService.create(user, dto.tipoId);
        return ResponseEntity.created(URI.create("/api/v1/usuarios/" + created.getId())).body(created);
    }

    @Operation(summary = "Buscar usuário por id")
    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @Operation(summary = "Atualizar dados do usuário (exceto senha)")
    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @Valid @RequestBody UserUpdateDto dto) {
        User data = UserMapper.updateDtoToEntity(dto);
        return userService.updateData(id, data, dto.tipoId);
    }

    @Operation(summary = "Excluir usuário")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @Operation(summary = "Trocar senha do usuário")
    @PatchMapping("/{id}/senha")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid @RequestBody PasswordUpdateDto dto) {
        userService.updatePassword(id, dto.novaSenha);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Associar/alterar tipo do usuário")
    @PatchMapping("/{id}/tipo")
    public User updateType(@PathVariable Long id, @Valid @RequestBody UserTypeAssociationDto dto) {
        return userService.updateType(id, dto.tipoId);
    }

    @Operation(summary = "Buscar usuários por nome")
    @GetMapping("/buscar")
    public List<User> search(@RequestParam String nome) {
        return userService.searchByName(nome);
    }

    @Operation(summary = "Validar login do usuário")
    @PostMapping("/login")
    public ResponseEntity<Void> validarLogin(@RequestParam String login, @RequestParam String senha) {
        boolean valido = userService.validarLogin(login, senha);
        return valido ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}

