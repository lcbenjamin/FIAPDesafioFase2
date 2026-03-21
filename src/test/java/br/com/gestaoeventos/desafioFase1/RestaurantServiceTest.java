package br.com.gestaoeventos.desafioFase1;

import br.com.gestaoeventos.desafioFase1.domain.Address;
import br.com.gestaoeventos.desafioFase1.domain.Restaurant;
import br.com.gestaoeventos.desafioFase1.domain.User;
import br.com.gestaoeventos.desafioFase1.repository.RestaurantRepository;
import br.com.gestaoeventos.desafioFase1.service.RestaurantService;
import br.com.gestaoeventos.desafioFase1.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class RestaurantServiceTest {

    @Test
    void createRestaurantAssignsOwnerAndSaves() {
        RestaurantRepository repo = Mockito.mock(RestaurantRepository.class);
        UserService userService = Mockito.mock(UserService.class);
        RestaurantService service = new RestaurantService(repo, userService);

        User dono = new User();
        dono.setId(10L);
        Mockito.when(userService.getById(10L)).thenReturn(dono);
        Mockito.when(repo.save(Mockito.any(Restaurant.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Restaurant restaurant = new Restaurant();
        restaurant.setNome("Bistro Azul");
        restaurant.setTipoCozinha("Francesa");
        restaurant.setHorarioFuncionamento("11:00-23:00");
        restaurant.setEndereco(address());

        Restaurant created = service.create(restaurant, 10L);

        assertEquals(10L, created.getDono().getId());
        assertEquals("Bistro Azul", created.getNome());
        Mockito.verify(repo).save(restaurant);
    }

    @Test
    void createRestaurantWithInvalidOwnerThrows() {
        RestaurantRepository repo = Mockito.mock(RestaurantRepository.class);
        UserService userService = Mockito.mock(UserService.class);
        RestaurantService service = new RestaurantService(repo, userService);

        Restaurant restaurant = new Restaurant();
        restaurant.setNome("Cozinha da Serra");
        restaurant.setTipoCozinha("Brasileira");
        restaurant.setHorarioFuncionamento("10:00-20:00");
        restaurant.setEndereco(address());

        Mockito.when(userService.getById(99L)).thenThrow(new IllegalArgumentException("Usuario nao encontrado"));

        assertThrows(IllegalArgumentException.class, () -> service.create(restaurant, 99L));
        Mockito.verify(repo, Mockito.never()).save(Mockito.any(Restaurant.class));
    }

    @Test
    void updateRestaurantUpdatesAllFields() {
        RestaurantRepository repo = Mockito.mock(RestaurantRepository.class);
        UserService userService = Mockito.mock(UserService.class);
        RestaurantService service = new RestaurantService(repo, userService);

        Restaurant existing = new Restaurant();
        existing.setId(1L);
        existing.setNome("Nome Antigo");
        existing.setTipoCozinha("Italiana");
        existing.setHorarioFuncionamento("08:00-18:00");
        existing.setEndereco(address());

        Restaurant updateData = new Restaurant();
        updateData.setNome("Nome Novo");
        updateData.setTipoCozinha("Japonesa");
        updateData.setHorarioFuncionamento("12:00-22:00");
        updateData.setEndereco(address());

        User novoDono = new User();
        novoDono.setId(2L);

        Mockito.when(repo.findById(1L)).thenReturn(Optional.of(existing));
        Mockito.when(userService.getById(2L)).thenReturn(novoDono);
        Mockito.when(repo.save(Mockito.any(Restaurant.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Restaurant updated = service.update(1L, updateData, 2L);

        assertEquals("Nome Novo", updated.getNome());
        assertEquals("Japonesa", updated.getTipoCozinha());
        assertEquals("12:00-22:00", updated.getHorarioFuncionamento());
        assertEquals(2L, updated.getDono().getId());
    }

    @Test
    void getByIdWhenNotFoundThrows() {
        RestaurantRepository repo = Mockito.mock(RestaurantRepository.class);
        UserService userService = Mockito.mock(UserService.class);
        RestaurantService service = new RestaurantService(repo, userService);

        Mockito.when(repo.findById(123L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> service.getById(123L));
    }

    private Address address() {
        Address address = new Address();
        address.setRua("Rua A");
        address.setNumero("123");
        address.setCidade("Sao Paulo");
        address.setCep("00000-000");
        return address;
    }
}

