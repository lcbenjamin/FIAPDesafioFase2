package br.com.gestaoeventos.desafioFase1;

import br.com.gestaoeventos.desafioFase1.domain.MenuItem;
import br.com.gestaoeventos.desafioFase1.domain.Restaurant;
import br.com.gestaoeventos.desafioFase1.repository.MenuItemRepository;
import br.com.gestaoeventos.desafioFase1.service.MenuItemService;
import br.com.gestaoeventos.desafioFase1.service.RestaurantService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MenuItemServiceTest {

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private RestaurantService restaurantService;

    @InjectMocks
    private MenuItemService menuItemService;

    @Test
    void shouldCreateMenuItemForExistingRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);

        MenuItem item = buildItem("Risoto de Cogumelos", 59.9);

        when(restaurantService.getById(1L)).thenReturn(restaurant);
        when(menuItemRepository.save(any(MenuItem.class))).thenAnswer(invocation -> invocation.getArgument(0));

        MenuItem created = menuItemService.create(item, 1L);

        assertEquals(restaurant, created.getRestaurante());
        assertEquals("Risoto de Cogumelos", created.getNome());
        verify(menuItemRepository).save(item);
    }

    @Test
    void shouldListItemsByRestaurantId() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(2L);

        List<MenuItem> items = List.of(
                buildItem("Item A", 20.0),
                buildItem("Item B", 35.5)
        );

        when(restaurantService.getById(2L)).thenReturn(restaurant);
        when(menuItemRepository.findByRestauranteId(2L)).thenReturn(items);

        List<MenuItem> result = menuItemService.listByRestaurante(2L);

        assertEquals(2, result.size());
        verify(restaurantService).getById(2L);
        verify(menuItemRepository).findByRestauranteId(2L);
    }

    @Test
    void shouldUpdateMenuItemData() {
        MenuItem existing = buildItem("Antigo", 10.0);
        existing.setId(11L);

        MenuItem newData = buildItem("Novo Prato", 44.9);
        newData.setDescricao("Descricao atualizada");
        newData.setApenasLocalizado(false);
        newData.setCaminhoFoto("/imagens/novo.jpg");

        Restaurant restaurant = new Restaurant();
        restaurant.setId(3L);

        when(menuItemRepository.findById(11L)).thenReturn(Optional.of(existing));
        when(restaurantService.getById(3L)).thenReturn(restaurant);
        when(menuItemRepository.save(existing)).thenReturn(existing);

        MenuItem updated = menuItemService.update(11L, newData, 3L);

        assertEquals("Novo Prato", updated.getNome());
        assertEquals(44.9, updated.getPreco());
        assertEquals("Descricao atualizada", updated.getDescricao());
        assertEquals("/imagens/novo.jpg", updated.getCaminhoFoto());
        assertEquals(restaurant, updated.getRestaurante());
        verify(menuItemRepository).save(existing);
    }

    @Test
    void shouldThrowWhenMenuItemNotFound() {
        when(menuItemRepository.findById(999L)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> menuItemService.getById(999L));

        assertEquals("Item do cardápio não encontrado", ex.getMessage());
    }

    @Test
    void shouldDeleteMenuItemWhenFound() {
        MenuItem existing = buildItem("Teste", 18.0);
        existing.setId(20L);

        when(menuItemRepository.findById(20L)).thenReturn(Optional.of(existing));

        menuItemService.delete(20L);

        verify(menuItemRepository).delete(existing);
    }

    private MenuItem buildItem(String nome, Double preco) {
        MenuItem item = new MenuItem();
        item.setNome(nome);
        item.setDescricao("Descricao");
        item.setPreco(preco);
        item.setApenasLocalizado(true);
        item.setCaminhoFoto("/imagens/prato.jpg");
        return item;
    }
}

