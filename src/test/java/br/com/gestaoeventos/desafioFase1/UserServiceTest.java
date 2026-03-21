package br.com.gestaoeventos.desafioFase1;

import br.com.gestaoeventos.desafioFase1.domain.Address;
import br.com.gestaoeventos.desafioFase1.domain.User;
import br.com.gestaoeventos.desafioFase1.domain.UserType;
import br.com.gestaoeventos.desafioFase1.repository.UserRepository;
import br.com.gestaoeventos.desafioFase1.service.UserService;
import br.com.gestaoeventos.desafioFase1.service.UserTypeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {


    @Test
    void validarLoginTest() {
        UserRepository repo = Mockito.mock(UserRepository.class);
        UserTypeService userTypeService = Mockito.mock(UserTypeService.class);
        UserService service = new UserService(repo, userTypeService);
        User u = new User();
        u.setLogin("marina");
        u.setSenha("123");
        Mockito.when(repo.findByLogin("marina")).thenReturn(java.util.Optional.of(u));
        assertTrue(service.validarLogin("marina","123"));
        assertFalse(service.validarLogin("marina","err"));
        assertFalse(service.validarLogin("bruna","123"));
    }

    @Test
    void createUserDuplicateEmailThrows() {
        UserRepository repo = Mockito.mock(UserRepository.class);
        UserTypeService userTypeService = Mockito.mock(UserTypeService.class);
        UserService service = new UserService(repo, userTypeService);
        UserType tipo = new UserType();
        tipo.setId(1L);
        tipo.setNomeTipo("Cliente");
        Mockito.when(userTypeService.getById(1L)).thenReturn(tipo);
        Mockito.when(repo.save(Mockito.any())).thenThrow(new org.springframework.dao.DataIntegrityViolationException("duplicate"));
        Address a = new Address(); a.setRua("Rua"); a.setNumero("1"); a.setCidade("Cidade"); a.setCep("00000-000");
        User u = new User(); u.setNome("A"); u.setEmail("a@a.com"); u.setLogin("a"); u.setSenha("s"); u.setEndereco(a);
        assertThrows(IllegalArgumentException.class, () -> service.create(u, 1L));
    }
}

