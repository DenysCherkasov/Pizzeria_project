package com.cherkasov.models.pizzaParts;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

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
public abstract class PizzaParts {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private PizzaPartsType pizzaPartsType;
    @Column(unique = true)
    private String name;
    @Valid
    @Min(value = 1, message = "Price must be a positive number")
    private int price;
    @Valid
    @Min(value = 1, message = "Weight must be a positive number")
    private int weight;

}

