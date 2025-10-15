package com.shyam_chindham.zomato_clone.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryPartnerDto {

    private Long id;
    private Long aadharNo;
    private String vehicleId;
    private UserDto user;
    private PointDto currentLocation;
    @JsonProperty("isAvailable")
    private Boolean isAvailable;
    private Double rating;
    @JsonIgnore
    private List<ConfirmedDeliveryDto> confirmedDeliveries;
}
