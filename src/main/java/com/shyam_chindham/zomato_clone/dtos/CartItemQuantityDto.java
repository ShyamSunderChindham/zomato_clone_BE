package com.shyam_chindham.zomato_clone.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemQuantityDto {
    private Long cartItemId;
    private Integer quantity;
    private Long cartId;
}
