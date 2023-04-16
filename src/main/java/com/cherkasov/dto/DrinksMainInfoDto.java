package com.cherkasov.dto;

import com.cherkasov.models.dish.DrinksType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class DrinksMainInfoDto {
    private String id;
    private String name;
    private String image;
    private int price;
    private int volume;
    private boolean availableStatus;
    private DrinksType drinksType;


}
