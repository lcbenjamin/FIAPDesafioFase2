package br.com.gestaoeventos.desafioFase1.api.mapper;

import br.com.gestaoeventos.desafioFase1.api.dto.MenuItemDto;
import br.com.gestaoeventos.desafioFase1.api.dto.MenuItemResponseDto;
import br.com.gestaoeventos.desafioFase1.domain.MenuItem;
import org.springframework.stereotype.Component;

@Component
public class MenuItemMapper {

    public static MenuItem toEntity(MenuItemDto dto) {
        MenuItem menuItem = new MenuItem();
        menuItem.setNome(dto.nome);
        menuItem.setDescricao(dto.descricao);
        menuItem.setPreco(dto.preco);
        menuItem.setApenasLocalizado(dto.apenasLocalizado);
        menuItem.setCaminhoFoto(dto.caminhoFoto);
        return menuItem;
    }

    public static MenuItem updateDtoToEntity(MenuItemDto dto) {
        MenuItem menuItem = new MenuItem();
        menuItem.setNome(dto.nome);
        menuItem.setDescricao(dto.descricao);
        menuItem.setPreco(dto.preco);
        menuItem.setApenasLocalizado(dto.apenasLocalizado);
        menuItem.setCaminhoFoto(dto.caminhoFoto);
        return menuItem;
    }

    public static MenuItemResponseDto toDto(MenuItem menuItem) {
        MenuItemResponseDto dto = new MenuItemResponseDto();
        dto.id = menuItem.getId();
        dto.nome = menuItem.getNome();
        dto.descricao = menuItem.getDescricao();
        dto.preco = menuItem.getPreco();
        dto.apenasLocalizado = menuItem.getApenasLocalizado();
        dto.caminhoFoto = menuItem.getCaminhoFoto();
        dto.restauranteId = menuItem.getRestaurante() != null ? menuItem.getRestaurante().getId() : null;
        return dto;
    }
}

