package com.shyam_chindham.zomato_clone.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcceptOrderDto {
    @NotBlank(message = "Estimated preparation time cannot be blank ")
    private Integer estimatedPreparationTime;
}
