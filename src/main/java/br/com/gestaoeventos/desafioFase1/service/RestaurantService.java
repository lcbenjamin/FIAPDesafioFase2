package br.com.gestaoeventos.desafioFase1.service;

import br.com.gestaoeventos.desafioFase1.domain.Restaurant;
import br.com.gestaoeventos.desafioFase1.domain.User;
import br.com.gestaoeventos.desafioFase1.repository.RestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final UserService userService;

    public RestaurantService(RestaurantRepository restaurantRepository, UserService userService) {
        this.restaurantRepository = restaurantRepository;
        this.userService = userService;
    }

    @Transactional
    public Restaurant create(Restaurant restaurant, Long donoId) {
        User dono = userService.getById(donoId);
        restaurant.setDono(dono);
        return restaurantRepository.save(restaurant);
    }

    public List<Restaurant> listAll() {
        return restaurantRepository.findAll();
    }

    public Restaurant getById(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante nao encontrado"));
    }

    @Transactional
    public Restaurant update(Long id, Restaurant data, Long donoId) {
        Restaurant restaurant = getById(id);
        User dono = userService.getById(donoId);
        restaurant.setNome(data.getNome());
        restaurant.setEndereco(data.getEndereco());
        restaurant.setTipoCozinha(data.getTipoCozinha());
        restaurant.setHorarioFuncionamento(data.getHorarioFuncionamento());
        restaurant.setDono(dono);
        return restaurantRepository.save(restaurant);
    }

    @Transactional
    public void delete(Long id) {
        Restaurant restaurant = getById(id);
        restaurantRepository.delete(restaurant);
    }
}

