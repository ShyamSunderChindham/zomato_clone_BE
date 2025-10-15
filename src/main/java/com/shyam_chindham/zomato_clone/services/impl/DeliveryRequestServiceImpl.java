package com.shyam_chindham.zomato_clone.services.impl;

import com.shyam_chindham.zomato_clone.entities.CustomerOrder;
import com.shyam_chindham.zomato_clone.entities.DeliveryRequest;
import com.shyam_chindham.zomato_clone.entities.enums.DeliveryRequestStatus;
import com.shyam_chindham.zomato_clone.exceptions.ResourceNotFoundException;
import com.shyam_chindham.zomato_clone.repositories.DeliveryRequestRepository;
import com.shyam_chindham.zomato_clone.services.DeliveryRequestService;
import com.shyam_chindham.zomato_clone.services.DistanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryRequestServiceImpl implements DeliveryRequestService {
    private  DeliveryRequestRepository deliveryRequestRepository;
    private  DistanceService distanceService;
    @Override
    public void createDeliveryRequest(CustomerOrder customerOrder) {
        //
        DeliveryRequest deliveryRequest = DeliveryRequest.builder()
                .deliveryRequestStatus(DeliveryRequestStatus.PENDING)
                .customerOrder(customerOrder)
                .distance(distanceService.calculateDistance(customerOrder.getCustomer().getDeliveryAddress(),customerOrder.getRestaurant().getAddress()))
                .grandTotal(customerOrder.getGrandTotal())
                .pickUpAddress(customerOrder.getRestaurant().getAddress())
                .dropOffAddress(customerOrder.getCustomer().getDeliveryAddress())
                .estimatedPreparationTime(customerOrder.getEstimatedPreparationTime())
                .build();
        //
        deliveryRequestRepository.save(deliveryRequest);
        //
        //TODO-Notify nearby DeliverPersons about the delivery request
    }

    @Override
    public DeliveryRequest updateDeliveryRequestStatus(DeliveryRequest deliveryRequest, DeliveryRequestStatus deliveryRequestStatus) {
        deliveryRequest.setDeliveryRequestStatus(deliveryRequestStatus);
        return deliveryRequestRepository.save(deliveryRequest);
    }

    @Override
    public DeliveryRequest getDeliveryRequestById(Long deliveryRequestId) {
        return deliveryRequestRepository.findById(deliveryRequestId).orElseThrow(
                ()->new ResourceNotFoundException("Delivery request not found by id:"+deliveryRequestId)
        );
    }
}
