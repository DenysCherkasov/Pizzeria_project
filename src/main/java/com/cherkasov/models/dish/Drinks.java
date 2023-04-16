package com.cherkasov.models.dish;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import javax.validation.constraints.Min;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Drinks extends Dish {
    @Enumerated(EnumType.STRING)
    private DrinksType drinksType;
    private String image;
    private String name;
    @Valid
    @Min(value = 1, message = "Volume must be a positive number")
    private int volume;

}
