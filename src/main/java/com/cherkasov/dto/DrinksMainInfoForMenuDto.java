package com.cherkasov.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class DrinksMainInfoForMenuDto {
    private String id;
    private String name;
    private String image;
    private int price;
    private int volume;

}
