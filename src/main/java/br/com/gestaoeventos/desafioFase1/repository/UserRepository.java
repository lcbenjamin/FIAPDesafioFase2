package br.com.gestaoeventos.desafioFase1.repository;

import br.com.gestaoeventos.desafioFase1.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByLogin(String login);
    List<User> findByNomeContainingIgnoreCase(String nome);
    long countByTipoId(Long tipoId);
}

