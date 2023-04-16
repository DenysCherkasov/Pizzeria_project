package com.cherkasov.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class DefaultPizzaMainInfoDto {
    private String id;
    private String name;
    private String image;
    private  String description;
    private int size;
    private int price;
    private int weight;
    private boolean availableStatus;

}
