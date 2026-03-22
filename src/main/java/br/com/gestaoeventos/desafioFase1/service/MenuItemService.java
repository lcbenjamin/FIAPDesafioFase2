package br.com.gestaoeventos.desafioFase1.service;

import br.com.gestaoeventos.desafioFase1.domain.MenuItem;
import br.com.gestaoeventos.desafioFase1.domain.Restaurant;
import br.com.gestaoeventos.desafioFase1.repository.MenuItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantService restaurantService;

    public MenuItemService(MenuItemRepository menuItemRepository, RestaurantService restaurantService) {
        this.menuItemRepository = menuItemRepository;
        this.restaurantService = restaurantService;
    }

    @Transactional
    public MenuItem create(MenuItem menuItem, Long restauranteId) {
        Restaurant restaurante = restaurantService.getById(restauranteId);
        menuItem.setRestaurante(restaurante);
        return menuItemRepository.save(menuItem);
    }

    public List<MenuItem> listAll() {
        return menuItemRepository.findAll();
    }

    public List<MenuItem> listByRestaurante(Long restauranteId) {
        restaurantService.getById(restauranteId);
        return menuItemRepository.findByRestauranteId(restauranteId);
    }

    public MenuItem getById(Long id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item do cardápio não encontrado"));
    }

    @Transactional
    public MenuItem update(Long id, MenuItem data, Long restauranteId) {
        MenuItem menuItem = getById(id);
        Restaurant restaurante = restaurantService.getById(restauranteId);
        menuItem.setNome(data.getNome());
        menuItem.setDescricao(data.getDescricao());
        menuItem.setPreco(data.getPreco());
        menuItem.setApenasLocalizado(data.getApenasLocalizado());
        menuItem.setCaminhoFoto(data.getCaminhoFoto());
        menuItem.setRestaurante(restaurante);
        return menuItemRepository.save(menuItem);
    }

    @Transactional
    public void delete(Long id) {
        MenuItem menuItem = getById(id);
        menuItemRepository.delete(menuItem);
    }
}

