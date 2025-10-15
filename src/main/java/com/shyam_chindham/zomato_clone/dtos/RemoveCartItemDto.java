package com.shyam_chindham.zomato_clone.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemoveCartItemDto {
    private Long cartItemId;
    private Long cartId;
}
