package com.cherkasov.repositories.drinksRepository;

import com.cherkasov.models.dish.Drinks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrinksRepository extends JpaRepository<Drinks, String>, CustomizedDrinksRepository {
}
