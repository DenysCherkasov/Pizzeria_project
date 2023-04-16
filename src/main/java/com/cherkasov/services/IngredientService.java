package com.cherkasov.services;

import com.cherkasov.dto.IngredientMainInfoDto;
import com.cherkasov.models.pizzaParts.Ingredient;
import com.cherkasov.repositories.ingredientRepository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public void save(final Ingredient ingredient) {
        ingredientRepository.save(ingredient);
    }

    public Iterable<Ingredient> getAll() {
        return ingredientRepository.findAll();
    }

    public Optional<Ingredient> getById(final String id) {
        return ingredientRepository.findById(id);
    }

    public List<IngredientMainInfoDto> getIngredientInfo() {
        return ingredientRepository.getIngredientInfo();
    }

    public void deleteById(final String id) {
        ingredientRepository.deleteById(id);
    }


}