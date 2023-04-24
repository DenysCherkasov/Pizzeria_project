package com.cherkasov.dto;

import com.cherkasov.models.dish.DefaultPizza;
import com.cherkasov.models.dish.Drinks;
import com.cherkasov.models.dish.OwnPizza;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class DishesFromCookieDto {
    private List<DefaultPizza> defaultPizzas;
    private List<OwnPizza> ownPizzas;
    private List<Drinks> drinks;
    private int price;
}
