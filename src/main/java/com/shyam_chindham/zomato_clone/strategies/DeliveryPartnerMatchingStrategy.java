package com.shyam_chindham.zomato_clone.strategies;

import com.shyam_chindham.zomato_clone.entities.DeliveryPartner;
import com.shyam_chindham.zomato_clone.entities.DeliveryRequest;

import java.util.List;

public interface DeliveryPartnerMatchingStrategy {
    List<DeliveryPartner> findMatchingDeliveryPartner(DeliveryRequest deliveryRequest);
}
