package com.cherkasov.services;

import com.cherkasov.models.dish.OwnPizza;
import com.cherkasov.models.dish.PizzaType;
import com.cherkasov.models.pizzaParts.Ingredient;
import com.cherkasov.models.pizzaParts.PizzaParts;
import com.cherkasov.models.pizzaParts.Souse;
import com.cherkasov.repositories.OwnPizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OwnPizzaService {
    private final OwnPizzaRepository ownPizzaRepository;
    private static final int PRICE_SMALL_PIZZA_BASE = 100;
    private static final int PRICE_BIG_PIZZA_BASE = 200;
    private static final int WEIGHT_SMALL_PIZZA_BASE = 200;
    private static final int WEIGHT_BIG_PIZZA_BASE = 400;


    @Autowired
    public OwnPizzaService(OwnPizzaRepository ownPizzaRepository) {
        this.ownPizzaRepository = ownPizzaRepository;
    }

    public OwnPizza save(int size, Souse souse, List<Ingredient> ingredients) {
        OwnPizza ownPizza = new OwnPizza();
        ownPizza.setPizzaType(PizzaType.OWN);
        ownPizza.setSize(size);
        ownPizza.setSouse(souse);
        ownPizza.setIngredients(ingredients);
        ownPizza.setPrice(calculatePrice(size, souse, ingredients));
        ownPizza.setWeight(calculateWeight(size, souse, ingredients));
        return ownPizzaRepository.saveAndFlush(ownPizza);
    }

    private int calculateWeight(int size, Souse souse, List<Ingredient> ingredients) {
        int weight = souse.getWeight();
        if (size == 30) {
            weight += WEIGHT_SMALL_PIZZA_BASE;
        } else {
            weight += WEIGHT_BIG_PIZZA_BASE;
        }
        int sumWeightOfIngredients = ingredients.stream()
                .mapToInt(PizzaParts::getWeight)
                .sum();
        return weight + sumWeightOfIngredients;
    }

    private int calculatePrice(int size, Souse souse, List<Ingredient> ingredients) {
        int price = souse.getPrice();
        if (size == 30) {
            price += PRICE_SMALL_PIZZA_BASE;
        } else {
            price += PRICE_BIG_PIZZA_BASE;
        }
        int sumPriceOfIngredients = ingredients.stream()
                .mapToInt(PizzaParts::getPrice)
                .sum();
        return price + sumPriceOfIngredients;
    }

    public Iterable<OwnPizza> getAll() {
        return ownPizzaRepository.findAll();
    }

    public Optional<OwnPizza> getById(final String id) {
        return ownPizzaRepository.findById(id);
    }

}
