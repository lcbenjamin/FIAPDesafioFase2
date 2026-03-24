package br.com.gestaoeventos.desafioFase1;

import br.com.gestaoeventos.desafioFase1.domain.UserType;
import br.com.gestaoeventos.desafioFase1.repository.UserRepository;
import br.com.gestaoeventos.desafioFase1.repository.UserTypeRepository;
import br.com.gestaoeventos.desafioFase1.service.UserTypeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserTypeServiceTest {

    @Mock
    private UserTypeRepository userTypeRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserTypeService userTypeService;

    @Test
    void shouldCreateUserTypeWhenNameIsUnique() {
        UserType userType = new UserType();
        userType.setNomeTipo("CLIENTE");

        when(userTypeRepository.findByNomeTipoIgnoreCase("CLIENTE")).thenReturn(Optional.empty());
        when(userTypeRepository.save(userType)).thenReturn(userType);

        UserType created = userTypeService.create(userType);

        assertEquals("CLIENTE", created.getNomeTipo());
        verify(userTypeRepository).save(userType);
    }

    @Test
    void shouldThrowWhenCreateWithDuplicatedName() {
        UserType newType = new UserType();
        newType.setNomeTipo("CLIENTE");

        UserType existing = new UserType();
        existing.setId(1L);
        existing.setNomeTipo("CLIENTE");

        when(userTypeRepository.findByNomeTipoIgnoreCase("CLIENTE")).thenReturn(Optional.of(existing));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> userTypeService.create(newType));

        assertEquals("Já existe um tipo de usuário com este nome", ex.getMessage());
        verify(userTypeRepository, never()).save(any(UserType.class));
    }

    @Test
    void shouldThrowWhenDeleteTypeWithLinkedUsers() {
        UserType existing = new UserType();
        existing.setId(5L);
        existing.setNomeTipo("DONO_RESTAURANTE");

        when(userTypeRepository.findById(5L)).thenReturn(Optional.of(existing));
        when(userRepository.countByTipoId(5L)).thenReturn(3L);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> userTypeService.delete(5L));

        assertEquals("Não é possível excluir o tipo. Há usuários associados a ele", ex.getMessage());
        verify(userTypeRepository, never()).delete(existing);
    }

    @Test
    void shouldUpdateTypeName() {
        UserType existing = new UserType();
        existing.setId(2L);
        existing.setNomeTipo("CLIENTE");

        UserType update = new UserType();
        update.setNomeTipo("DONO_RESTAURANTE");

        when(userTypeRepository.findById(2L)).thenReturn(Optional.of(existing));
        when(userTypeRepository.findByNomeTipoIgnoreCase("DONO_RESTAURANTE")).thenReturn(Optional.empty());
        when(userTypeRepository.save(existing)).thenReturn(existing);

        UserType updated = userTypeService.update(2L, update);

        assertEquals("DONO_RESTAURANTE", updated.getNomeTipo());
        verify(userTypeRepository).save(existing);
    }

    @Test
    void shouldTranslateIntegrityViolationToBusinessError() {
        UserType userType = new UserType();
        userType.setNomeTipo("CLIENTE");

        when(userTypeRepository.findByNomeTipoIgnoreCase("CLIENTE")).thenReturn(Optional.empty());
        when(userTypeRepository.save(userType)).thenThrow(new DataIntegrityViolationException("duplicate"));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> userTypeService.create(userType));

        assertEquals("Já existe um tipo de usuário com este nome", ex.getMessage());
    }
}

