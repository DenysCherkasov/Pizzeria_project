package com.cherkasov.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class OrderDishForCookieDto {
    private String dishName;
    private String dishId;

}
