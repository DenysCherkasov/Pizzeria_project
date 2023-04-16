package com.cherkasov.models.dish;

import com.cherkasov.models.order.Order;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Dish {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @Enumerated(EnumType.STRING)
    private DishType dishType;
    @Valid
    @Min(value = 1, message = "Price must be a positive number")
    private int price;
    private boolean availableStatus;
    @ManyToMany (mappedBy = "dishes")
    private List<Order> orders;


}
