package com.cherkasov.repositories.ingredientRepository;

import com.cherkasov.dto.IngredientMainInfoDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class CustomizedIngredientRepositoryImpl implements CustomizedIngredientRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<IngredientMainInfoDto> getIngredientInfo() {
        return entityManager.createQuery(
                        "SELECT new com.cherkasov.dto.IngredientMainInfoDto " +
                                "(i.id, i.name, i.price, i.weight, i.vegetarian) " +
                                "FROM Ingredient i ORDER BY i.name",
                        IngredientMainInfoDto.class)
                .getResultList();
    }

}
