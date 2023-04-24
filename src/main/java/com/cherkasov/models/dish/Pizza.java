package com.cherkasov.models.dish;

import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Pizza extends Dish {
    @Enumerated(EnumType.STRING)
    private PizzaType pizzaType;
    @Valid
    @Min(value = 1, message = "Size must be a positive number")
    private int size;
    @Valid
    @Min(value = 1, message = "Weight must be a positive number")
    private int weight;
}
