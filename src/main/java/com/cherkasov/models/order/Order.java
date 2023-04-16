package com.cherkasov.models.order;

import com.cherkasov.models.delivery.Delivery;
import com.cherkasov.models.dish.Dish;
import com.cherkasov.models.user.User;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "orders_dishes",
            joinColumns = @JoinColumn(name = "order_identifier"),
            inverseJoinColumns = @JoinColumn(name = "dish_identifier"))
    private List<Dish> dishes;
    private int price;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;
    private LocalDate dateCreation;
}
