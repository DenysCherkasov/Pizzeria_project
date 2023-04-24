package com.cherkasov.models.delivery;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String receiverName;
    private String receiverPhone;
    private String deliveryTime;
    private String deliveryAddress;

    public Delivery(String receiverName, String receiverPhone, String deliveryAddress, String deliveryTime) {
        this.receiverName = receiverName;
        this.receiverPhone = receiverPhone;
        this.deliveryAddress = deliveryAddress;
        this.deliveryTime = deliveryTime;
    }
}
