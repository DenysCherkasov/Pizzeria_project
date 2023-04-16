package com.cherkasov.repositories.ingredientRepository;

import com.cherkasov.dto.IngredientMainInfoDto;

import java.util.List;

public interface CustomizedIngredientRepository {
    List <IngredientMainInfoDto> getIngredientInfo();

}
