package com.cherkasov.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class OrderStatisticDto {
    private long countOrders;
    private long totalIncome;

}
