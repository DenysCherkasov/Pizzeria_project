package com.cherkasov.models.pizzaParts;

import com.cherkasov.models.dish.OwnPizza;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Ingredient extends PizzaParts {
    private boolean vegetarian;
    @ManyToMany(mappedBy = "ingredients")
    private List<OwnPizza> pizzas;


}
