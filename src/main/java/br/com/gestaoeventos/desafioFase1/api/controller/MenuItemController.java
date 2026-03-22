package br.com.gestaoeventos.desafioFase1.api.controller;

import br.com.gestaoeventos.desafioFase1.api.dto.MenuItemDto;
import br.com.gestaoeventos.desafioFase1.api.dto.MenuItemResponseDto;
import br.com.gestaoeventos.desafioFase1.api.mapper.MenuItemMapper;
import br.com.gestaoeventos.desafioFase1.domain.MenuItem;
import br.com.gestaoeventos.desafioFase1.service.MenuItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Itens do Cardápio")
@RestController
@RequestMapping("/api/v1/itens-cardapio")
public class MenuItemController {

    private final MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @Operation(summary = "Cadastrar item do cardápio")
    @PostMapping
    public ResponseEntity<MenuItemResponseDto> create(@Valid @RequestBody MenuItemDto dto) {
        MenuItem menuItem = MenuItemMapper.toEntity(dto);
        MenuItem created = menuItemService.create(menuItem, dto.restauranteId);
        return ResponseEntity.created(URI.create("/api/v1/itens-cardapio/" + created.getId()))
                .body(MenuItemMapper.toDto(created));
    }

    @Operation(summary = "Listar todos os itens do cardápio")
    @GetMapping
    public List<MenuItemResponseDto> listAll() {
        return menuItemService.listAll().stream()
                .map(MenuItemMapper::toDto)
                .toList();
    }

    @Operation(summary = "Listar itens do cardápio por restaurante")
    @GetMapping("/restaurante/{restauranteId}")
    public List<MenuItemResponseDto> listByRestaurante(@PathVariable Long restauranteId) {
        return menuItemService.listByRestaurante(restauranteId).stream()
                .map(MenuItemMapper::toDto)
                .toList();
    }

    @Operation(summary = "Buscar item do cardápio por id")
    @GetMapping("/{id}")
    public MenuItemResponseDto getById(@PathVariable Long id) {
        return MenuItemMapper.toDto(menuItemService.getById(id));
    }

    @Operation(summary = "Atualizar item do cardápio")
    @PutMapping("/{id}")
    public MenuItemResponseDto update(@PathVariable Long id, @Valid @RequestBody MenuItemDto dto) {
        MenuItem data = MenuItemMapper.updateDtoToEntity(dto);
        return MenuItemMapper.toDto(menuItemService.update(id, data, dto.restauranteId));
    }

    @Operation(summary = "Excluir item do cardápio")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        menuItemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

