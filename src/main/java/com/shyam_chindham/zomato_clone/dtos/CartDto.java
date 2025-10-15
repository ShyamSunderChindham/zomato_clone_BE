package com.shyam_chindham.zomato_clone.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shyam_chindham.zomato_clone.entities.enums.CartStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {

    private Long id;
    private String cartName;
    private List<CartItemDto> cartItems;
    private Double totalAmount;
    private CartStatus cartStatus;
    @JsonIgnore
    private CustomerDto customer;
    @JsonIgnore
    private RestaurantDto restaurant;
}
