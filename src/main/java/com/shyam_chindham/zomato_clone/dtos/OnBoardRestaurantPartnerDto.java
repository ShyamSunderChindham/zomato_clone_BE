package com.shyam_chindham.zomato_clone.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnBoardRestaurantPartnerDto {
    @NotNull(message = "Adhar number should be provided")
    private Long aadharNo;
}
