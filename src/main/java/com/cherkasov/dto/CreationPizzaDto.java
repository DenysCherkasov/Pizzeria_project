package com.cherkasov.dto;

import com.cherkasov.models.pizzaParts.Ingredient;
import com.cherkasov.models.pizzaParts.Souse;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CreationPizzaDto {
    private int size;
    private Souse souse;
    private List<Ingredient> ingredients;
}
