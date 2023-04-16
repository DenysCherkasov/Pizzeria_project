package com.cherkasov.models.dish;

import com.cherkasov.models.pizzaParts.Ingredient;
import com.cherkasov.models.pizzaParts.Souse;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OwnPizza extends Pizza {
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "own_pizza_ingredients",
            joinColumns = @JoinColumn(name = "order_identifier"),
            inverseJoinColumns = @JoinColumn(name = "dish_identifier"))
    private List<Ingredient> ingredients;
    @ManyToOne
    @JoinColumn(name = "souse_id")
    private Souse souse;
}
