package br.com.gestaoeventos.desafioFase1.repository;

import br.com.gestaoeventos.desafioFase1.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}

