package br.com.gestaoeventos.desafioFase1.service;

import br.com.gestaoeventos.desafioFase1.domain.User;
import br.com.gestaoeventos.desafioFase1.domain.UserType;
import br.com.gestaoeventos.desafioFase1.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserTypeService userTypeService;

    public UserService(UserRepository userRepository, UserTypeService userTypeService) {
        this.userRepository = userRepository;
        this.userTypeService = userTypeService;
    }

    @Transactional
    public User create(User user, Long tipoId) {
        try {
            UserType tipo = userTypeService.getById(tipoId);
            user.setTipo(tipo);
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("E-mail já cadastrado");
        }
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
    }

    public List<User> searchByName(String nome) {
        return userRepository.findByNomeContainingIgnoreCase(nome);
    }

    @Transactional
    public User updateData(Long id, User data, Long tipoId) {
        User user = getById(id);
        UserType tipo = userTypeService.getById(tipoId);
        user.setNome(data.getNome());
        user.setEndereco(data.getEndereco());
        user.setTipo(tipo);
        return userRepository.save(user);
    }

    @Transactional
    public User updateType(Long id, Long tipoId) {
        User user = getById(id);
        UserType tipo = userTypeService.getById(tipoId);
        user.setTipo(tipo);
        return userRepository.save(user);
    }

    @Transactional
    public void updatePassword(Long id, String novaSenha) {
        User user = getById(id);
        user.setSenha(novaSenha);
        userRepository.save(user);
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public boolean validarLogin(String login, String senha) {
        return userRepository.findByLogin(login)
                .map(u -> u.getSenha().equals(senha))
                .orElse(false);
    }
}

