package com.cherkasov.models.pizzaParts;


import com.cherkasov.models.dish.OwnPizza;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Souse extends PizzaParts {
    private boolean spice;
    @OneToMany(mappedBy = "souse", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<OwnPizza> pizzas;
}
