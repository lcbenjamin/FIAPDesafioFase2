package br.com.gestaoeventos.desafioFase1.api.controller;

import br.com.gestaoeventos.desafioFase1.api.dto.RestaurantDto;
import br.com.gestaoeventos.desafioFase1.api.dto.RestaurantResponseDto;
import br.com.gestaoeventos.desafioFase1.api.mapper.RestaurantMapper;
import br.com.gestaoeventos.desafioFase1.domain.Restaurant;
import br.com.gestaoeventos.desafioFase1.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Restaurantes")
@RestController
@RequestMapping("/api/v1/restaurantes")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @Operation(summary = "Cadastrar restaurante")
    @PostMapping
    public ResponseEntity<RestaurantResponseDto> create(@Valid @RequestBody RestaurantDto dto) {
        Restaurant restaurant = RestaurantMapper.toEntity(dto);
        Restaurant created = restaurantService.create(restaurant, dto.donoId);
        return ResponseEntity.created(URI.create("/api/v1/restaurantes/" + created.getId()))
                .body(RestaurantMapper.toDto(created));
    }

    @Operation(summary = "Listar restaurantes")
    @GetMapping
    public List<RestaurantResponseDto> listAll() {
        return restaurantService.listAll().stream()
                .map(RestaurantMapper::toDto)
                .toList();
    }

    @Operation(summary = "Buscar restaurante por id")
    @GetMapping("/{id}")
    public RestaurantResponseDto getById(@PathVariable Long id) {
        return RestaurantMapper.toDto(restaurantService.getById(id));
    }

    @Operation(summary = "Atualizar restaurante")
    @PutMapping("/{id}")
    public RestaurantResponseDto update(@PathVariable Long id, @Valid @RequestBody RestaurantDto dto) {
        Restaurant data = RestaurantMapper.updateDtoToEntity(dto);
        return RestaurantMapper.toDto(restaurantService.update(id, data, dto.donoId));
    }

    @Operation(summary = "Excluir restaurante")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        restaurantService.delete(id);
    }
}

