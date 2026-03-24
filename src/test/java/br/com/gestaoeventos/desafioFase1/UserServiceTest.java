package br.com.gestaoeventos.desafioFase1;

import br.com.gestaoeventos.desafioFase1.domain.Address;
import br.com.gestaoeventos.desafioFase1.domain.User;
import br.com.gestaoeventos.desafioFase1.domain.UserType;
import br.com.gestaoeventos.desafioFase1.repository.UserRepository;
import br.com.gestaoeventos.desafioFase1.service.UserService;
import br.com.gestaoeventos.desafioFase1.service.UserTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserTypeService userTypeService;

    @InjectMocks
    private UserService userService;

    private UserType cliente;

    @BeforeEach
    void setUp() {
        cliente = new UserType();
        cliente.setId(1L);
        cliente.setNomeTipo("CLIENTE");
    }

    @Test
    void shouldCreateUserWhenEmailIsUnique() {
        User user = buildUser();

        when(userTypeService.getById(1L)).thenReturn(cliente);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User created = userService.create(user, 1L);

        assertNotNull(created);
        assertEquals(cliente, created.getTipo());
        verify(userTypeService).getById(1L);
        verify(userRepository).save(user);
    }

    @Test
    void shouldThrowIllegalArgumentWhenCreateWithDuplicatedEmail() {
        User user = buildUser();

        when(userTypeService.getById(1L)).thenReturn(cliente);
        when(userRepository.save(any(User.class))).thenThrow(new DataIntegrityViolationException("duplicate"));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> userService.create(user, 1L));

        assertEquals("E-mail já cadastrado", ex.getMessage());
    }

    @Test
    void shouldUpdateUserDataAndType() {
        User existing = buildUser();
        existing.setId(10L);

        User updateData = new User();
        updateData.setNome("Novo Nome");
        updateData.setEndereco(buildAddress("Rua Nova", "99"));

        UserType dono = new UserType();
        dono.setId(2L);
        dono.setNomeTipo("DONO_RESTAURANTE");

        when(userRepository.findById(10L)).thenReturn(Optional.of(existing));
        when(userTypeService.getById(2L)).thenReturn(dono);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User updated = userService.updateData(10L, updateData, 2L);

        assertEquals("Novo Nome", updated.getNome());
        assertEquals("Rua Nova", updated.getEndereco().getRua());
        assertEquals(dono, updated.getTipo());
        verify(userRepository).save(existing);
    }

    @Test
    void shouldUpdatePassword() {
        User existing = buildUser();
        existing.setId(20L);

        when(userRepository.findById(20L)).thenReturn(Optional.of(existing));

        userService.updatePassword(20L, "novaSenha123");

        assertEquals("novaSenha123", existing.getSenha());
        verify(userRepository).save(existing);
    }

    @Test
    void shouldValidateLogin() {
        User existing = buildUser();

        when(userRepository.findByLogin("lucas")).thenReturn(Optional.of(existing));
        when(userRepository.findByLogin("naoexiste")).thenReturn(Optional.empty());

        assertTrue(userService.validarLogin("lucas", "123456"));
        assertFalse(userService.validarLogin("lucas", "senhaErrada"));
        assertFalse(userService.validarLogin("naoexiste", "123456"));
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> userService.getById(999L));

        assertEquals("Usuário não encontrado", ex.getMessage());
    }

    private User buildUser() {
        User user = new User();
        user.setNome("Lucas Costa");
        user.setEmail("lucas@email.com");
        user.setLogin("lucas");
        user.setSenha("123456");
        user.setEndereco(buildAddress("Rua Manoel Borba", "1000"));
        user.setTipo(cliente);
        return user;
    }

    private Address buildAddress(String rua, String numero) {
        Address address = new Address();
        address.setRua(rua);
        address.setNumero(numero);
        address.setCidade("Recife");
        address.setCep("090890-001");
        return address;
    }
}

