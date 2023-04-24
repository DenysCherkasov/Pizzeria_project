package com.cherkasov.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class SouseMainInfoDto {
    private String id;
    private String name;
    private int price;
    private int weight;
    private boolean spice;

}
