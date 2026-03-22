package br.com.gestaoeventos.desafioFase1;

import br.com.gestaoeventos.desafioFase1.domain.MenuItem;
import br.com.gestaoeventos.desafioFase1.domain.Restaurant;
import br.com.gestaoeventos.desafioFase1.repository.MenuItemRepository;
import br.com.gestaoeventos.desafioFase1.service.MenuItemService;
import br.com.gestaoeventos.desafioFase1.service.RestaurantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class MenuItemServiceTest {

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Test
    public void testCreateMenuItem() {
        // Dado um restaurante existente (usar um id que existe no BD)
        Long restauranteId = 1L;
        
        // Quando crio um item do cardápio
        MenuItem menuItem = new MenuItem();
        menuItem.setNome("Pastel");
        menuItem.setDescricao("Pastel de queijo quentinho");
        menuItem.setPreco(8.50);
        menuItem.setApenasLocalizado(true);
        menuItem.setCaminhoFoto("https://example.com/pastel.jpg");
        
        MenuItem created = menuItemService.create(menuItem, restauranteId);
        
        // Então o item deve ser criado com sucesso
        assertNotNull(created.getId());
        assertEquals("Pastel", created.getNome());
        assertEquals(8.50, created.getPreco());
        assertEquals(true, created.getApenasLocalizado());
    }

    @Test
    public void testGetMenuItemById() {
        Long restauranteId = 1L;
        
        // Criar um item
        MenuItem menuItem = new MenuItem();
        menuItem.setNome("Hambúrguer");
        menuItem.setDescricao("Hambúrguer artesanal");
        menuItem.setPreco(25.00);
        menuItem.setApenasLocalizado(false);
        menuItem.setCaminhoFoto("https://example.com/hamburger.jpg");
        
        MenuItem created = menuItemService.create(menuItem, restauranteId);
        
        // Buscar o item criado
        MenuItem found = menuItemService.getById(created.getId());
        
        assertEquals("Hambúrguer", found.getNome());
        assertEquals(25.00, found.getPreco());
    }

    @Test
    public void testUpdateMenuItem() {
        Long restauranteId = 1L;
        
        // Criar um item
        MenuItem menuItem = new MenuItem();
        menuItem.setNome("Pizza");
        menuItem.setDescricao("Pizza tradicional");
        menuItem.setPreco(30.00);
        menuItem.setApenasLocalizado(true);
        menuItem.setCaminhoFoto("https://example.com/pizza.jpg");
        
        MenuItem created = menuItemService.create(menuItem, restauranteId);
        
        // Atualizar o item
        MenuItem updated = new MenuItem();
        updated.setNome("Pizza Premium");
        updated.setDescricao("Pizza premium com mais cobertura");
        updated.setPreco(45.00);
        updated.setApenasLocalizado(true);
        updated.setCaminhoFoto("https://example.com/pizza-premium.jpg");
        
        MenuItem result = menuItemService.update(created.getId(), updated, restauranteId);
        
        assertEquals("Pizza Premium", result.getNome());
        assertEquals(45.00, result.getPreco());
    }

    @Test
    public void testDeleteMenuItem() {
        Long restauranteId = 1L;
        
        // Criar um item
        MenuItem menuItem = new MenuItem();
        menuItem.setNome("Refrigerante");
        menuItem.setDescricao("Refrigerante 2L");
        menuItem.setPreco(12.00);
        menuItem.setApenasLocalizado(false);
        menuItem.setCaminhoFoto("https://example.com/refrigerante.jpg");
        
        MenuItem created = menuItemService.create(menuItem, restauranteId);
        Long id = created.getId();
        
        // Deletar o item
        menuItemService.delete(id);
        
        // Verificar se foi deletado
        assertThrows(IllegalArgumentException.class, () -> menuItemService.getById(id));
    }

    @Test
    public void testGetMenuItemNotFound() {
        assertThrows(IllegalArgumentException.class, () -> menuItemService.getById(9999L));
    }
}

