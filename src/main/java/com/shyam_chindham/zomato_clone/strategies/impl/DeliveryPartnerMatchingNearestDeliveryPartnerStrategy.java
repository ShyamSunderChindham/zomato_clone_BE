package com.shyam_chindham.zomato_clone.strategies.impl;


import com.shyam_chindham.zomato_clone.entities.DeliveryPartner;
import com.shyam_chindham.zomato_clone.entities.DeliveryRequest;
import com.shyam_chindham.zomato_clone.repositories.DeliveryPartnerRepository;
import com.shyam_chindham.zomato_clone.strategies.DeliveryPartnerMatchingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryPartnerMatchingNearestDeliveryPartnerStrategy implements DeliveryPartnerMatchingStrategy {
    private  DeliveryPartnerRepository deliveryPartnerRepository;
    @Override
    public List<DeliveryPartner> findMatchingDeliveryPartner(DeliveryRequest deliveryRequest) {
        return deliveryPartnerRepository.findTenNearestDeliveryPartner(deliveryRequest.getPickUpAddress());
    }
}
