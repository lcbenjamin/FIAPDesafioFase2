package br.com.gestaoeventos.desafioFase1.api.mapper;

import br.com.gestaoeventos.desafioFase1.api.dto.RestaurantDto;
import br.com.gestaoeventos.desafioFase1.api.dto.RestaurantResponseDto;
import br.com.gestaoeventos.desafioFase1.domain.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMapper {

    public static Restaurant toEntity(RestaurantDto dto) {
        Restaurant restaurant = new Restaurant();
        restaurant.setNome(dto.nome);
        restaurant.setEndereco(dto.endereco);
        restaurant.setTipoCozinha(dto.tipoCozinha);
        restaurant.setHorarioFuncionamento(dto.horarioFuncionamento);
        return restaurant;
    }

    public static Restaurant updateDtoToEntity(RestaurantDto dto) {
        Restaurant restaurant = new Restaurant();
        restaurant.setNome(dto.nome);
        restaurant.setEndereco(dto.endereco);
        restaurant.setTipoCozinha(dto.tipoCozinha);
        restaurant.setHorarioFuncionamento(dto.horarioFuncionamento);
        return restaurant;
    }

    public static RestaurantResponseDto toDto(Restaurant restaurant) {
        RestaurantResponseDto dto = new RestaurantResponseDto();
        dto.id = restaurant.getId();
        dto.nome = restaurant.getNome();
        dto.endereco = restaurant.getEndereco();
        dto.tipoCozinha = restaurant.getTipoCozinha();
        dto.horarioFuncionamento = restaurant.getHorarioFuncionamento();
        dto.donoId = restaurant.getDono() != null ? restaurant.getDono().getId() : null;
        return dto;
    }
}

