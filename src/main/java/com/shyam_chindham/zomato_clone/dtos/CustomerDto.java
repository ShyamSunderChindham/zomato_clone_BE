package com.shyam_chindham.zomato_clone.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    private Long id;

    private UserDto user;

    private PointDto deliveryAddress;

    @JsonIgnore
    private List<OrderRequestDto> orders;

    private Double rating;
}
