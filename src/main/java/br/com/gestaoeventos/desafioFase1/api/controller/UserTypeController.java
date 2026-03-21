package br.com.gestaoeventos.desafioFase1.api.controller;

import br.com.gestaoeventos.desafioFase1.api.dto.UserTypeDto;
import br.com.gestaoeventos.desafioFase1.domain.UserType;
import br.com.gestaoeventos.desafioFase1.service.UserTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Tipos de Usuário")
@RestController
@RequestMapping("/api/v1/tipos-usuario")
public class UserTypeController {

    private final UserTypeService userTypeService;

    public UserTypeController(UserTypeService userTypeService) {
        this.userTypeService = userTypeService;
    }

    @Operation(summary = "Cadastrar tipo de usuário")
    @PostMapping
    public ResponseEntity<UserType> create(@Valid @RequestBody UserTypeDto dto) {
        UserType tipo = new UserType();
        tipo.setNomeTipo(dto.nomeTipo);
        UserType created = userTypeService.create(tipo);
        return ResponseEntity.created(URI.create("/api/v1/tipos-usuario/" + created.getId())).body(created);
    }

    @Operation(summary = "Listar tipos de usuário")
    @GetMapping
    public List<UserType> listAll() {
        return userTypeService.listAll();
    }

    @Operation(summary = "Buscar tipo de usuário por id")
    @GetMapping("/{id}")
    public UserType getById(@PathVariable Long id) {
        return userTypeService.getById(id);
    }

    @Operation(summary = "Atualizar tipo de usuário")
    @PutMapping("/{id}")
    public UserType update(@PathVariable Long id, @Valid @RequestBody UserTypeDto dto) {
        UserType tipo = new UserType();
        tipo.setNomeTipo(dto.nomeTipo);
        return userTypeService.update(id, tipo);
    }

    @Operation(summary = "Excluir tipo de usuário")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userTypeService.delete(id);
    }
}

