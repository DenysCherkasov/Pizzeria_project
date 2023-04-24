package com.cherkasov.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class OrderMainInfoDto {
    private String id;
    private LocalDate dateCreation;
    private int price;
    private long countDishes;
    private String receiverName;
    private String receiverPhone;
    private String deliveryTime;
    private String deliveryAddress;

}
