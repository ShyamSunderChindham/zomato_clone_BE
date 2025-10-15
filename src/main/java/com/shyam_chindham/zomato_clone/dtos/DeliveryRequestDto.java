package com.shyam_chindham.zomato_clone.dtos;

import com.shyam_chindham.zomato_clone.entities.enums.DeliveryRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryRequestDto {

    private Long id;

    private CustomerOrderDto customerOrder;

    private Double distance;

    private Double grandTotal;

    private Integer estimatedPreparationTime;

    private DeliveryRequestStatus deliveryRequestStatus;

    private PointDto pickUpAddress;

    private PointDto dropOffAddress;

    private LocalDateTime deliveryRequestedTime;
}
