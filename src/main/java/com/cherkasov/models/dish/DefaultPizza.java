package com.cherkasov.models.dish;

import lombok.*;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DefaultPizza extends Pizza {
    private String name;
    private  String description;
    private String image;
}
