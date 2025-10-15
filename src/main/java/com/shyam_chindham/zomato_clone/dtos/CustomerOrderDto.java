package com.shyam_chindham.zomato_clone.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shyam_chindham.zomato_clone.entities.enums.CustomerOrderStatus;
import com.shyam_chindham.zomato_clone.entities.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerOrderDto {

    private Long id;
    private CustomerOrderStatus customerOrderStatus;
    //
    @JsonIgnore
    private CartDto cart;
    private Double totalAmount;
    private Double deliveryCharges;
    private Double platformFee;
    private Double grandTotal;
    @JsonIgnore
    private RestaurantDto restaurant;
    private CustomerDto customer;
    private PaymentMethod paymentMethod;
    private Integer estimatedPreparationTime;
    //
    private String otp;
    //
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderAcceptedTime;
}
