package br.com.gestaoeventos.desafioFase1.repository;

import br.com.gestaoeventos.desafioFase1.domain.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserTypeRepository extends JpaRepository<UserType, Long> {
    Optional<UserType> findByNomeTipoIgnoreCase(String nomeTipo);
}

