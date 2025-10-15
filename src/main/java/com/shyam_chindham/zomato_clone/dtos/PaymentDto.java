package com.shyam_chindham.zomato_clone.dtos;

import com.shyam_chindham.zomato_clone.entities.enums.PaymentMethod;
import com.shyam_chindham.zomato_clone.entities.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {

    private Long id;

    private PaymentMethod paymentMethod;

    private CustomerOrderDto customerOrder;

    private PaymentStatus paymentStatus;

    private double amount;

    private LocalDateTime paymentTime;
}
