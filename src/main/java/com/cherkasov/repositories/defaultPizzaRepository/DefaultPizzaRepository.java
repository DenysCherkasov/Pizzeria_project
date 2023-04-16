package com.cherkasov.repositories.defaultPizzaRepository;

import com.cherkasov.models.dish.DefaultPizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DefaultPizzaRepository extends JpaRepository<DefaultPizza, String>, CustomizedDefaultPizzaRepository {
}

