package br.com.gestaoeventos.desafioFase1.repository;

import br.com.gestaoeventos.desafioFase1.domain.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByRestauranteId(Long restauranteId);
}

