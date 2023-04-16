package com.cherkasov.repositories.ingredientRepository;

import com.cherkasov.models.pizzaParts.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, String>, CustomizedIngredientRepository  {

}
