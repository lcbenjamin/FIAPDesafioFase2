package br.com.gestaoeventos.desafioFase1;

import br.com.gestaoeventos.desafioFase1.domain.Address;
import br.com.gestaoeventos.desafioFase1.domain.Restaurant;
import br.com.gestaoeventos.desafioFase1.domain.User;
import br.com.gestaoeventos.desafioFase1.repository.RestaurantRepository;
import br.com.gestaoeventos.desafioFase1.service.RestaurantService;
import br.com.gestaoeventos.desafioFase1.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private RestaurantService restaurantService;

    @Test
    void shouldCreateRestaurantWithExistingOwner() {
        User owner = buildOwner(1L);
        Restaurant restaurant = buildRestaurant("Restaurante da Neide", "Brasileira", "11:00-23:00");

        when(userService.getById(1L)).thenReturn(owner);
        when(restaurantRepository.save(any(Restaurant.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Restaurant created = restaurantService.create(restaurant, 1L);

        assertEquals(owner, created.getDono());
        assertEquals("Restaurante da Neide", created.getNome());
        verify(restaurantRepository).save(restaurant);
    }

    @Test
    void shouldUpdateRestaurantData() {
        Restaurant existing = buildRestaurant("Antigo", "Caseira", "10:00-18:00");
        existing.setId(7L);

        Restaurant newData = buildRestaurant("Novo Nome", "Italiana", "11:00-22:00");
        User newOwner = buildOwner(2L);

        when(restaurantRepository.findById(7L)).thenReturn(Optional.of(existing));
        when(userService.getById(2L)).thenReturn(newOwner);
        when(restaurantRepository.save(existing)).thenReturn(existing);

        Restaurant updated = restaurantService.update(7L, newData, 2L);

        assertEquals("Novo Nome", updated.getNome());
        assertEquals("Italiana", updated.getTipoCozinha());
        assertEquals("11:00-22:00", updated.getHorarioFuncionamento());
        assertEquals(newOwner, updated.getDono());
        verify(restaurantRepository).save(existing);
    }

    @Test
    void shouldThrowWhenRestaurantNotFound() {
        when(restaurantRepository.findById(99L)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> restaurantService.getById(99L));

        assertEquals("Restaurante nao encontrado", ex.getMessage());
    }

    @Test
    void shouldDeleteRestaurantWhenFound() {
        Restaurant existing = buildRestaurant("Teste", "Fast Food", "09:00-21:00");
        existing.setId(15L);

        when(restaurantRepository.findById(15L)).thenReturn(Optional.of(existing));

        restaurantService.delete(15L);

        verify(restaurantRepository).delete(existing);
    }

    private Restaurant buildRestaurant(String nome, String tipoCozinha, String horario) {
        Restaurant restaurant = new Restaurant();
        restaurant.setNome(nome);
        restaurant.setTipoCozinha(tipoCozinha);
        restaurant.setHorarioFuncionamento(horario);
        restaurant.setEndereco(buildAddress());
        return restaurant;
    }

    private Address buildAddress() {
        Address address = new Address();
        address.setRua("Rua Neide Silva");
        address.setNumero("456");
        address.setCidade("Olinda");
        address.setCep("02000-000");
        return address;
    }

    private User buildOwner(Long id) {
        User user = new User();
        user.setId(id);
        user.setNome("Dono " + id);
        return user;
    }
}

