package com.shyam_chindham.zomato_clone.services;

import com.shyam_chindham.zomato_clone.entities.CustomerOrder;
import com.shyam_chindham.zomato_clone.entities.DeliveryRequest;
import com.shyam_chindham.zomato_clone.entities.enums.DeliveryRequestStatus;

public interface DeliveryRequestService {
    void createDeliveryRequest(CustomerOrder customerOrder);
    DeliveryRequest updateDeliveryRequestStatus(DeliveryRequest deliveryRequest,DeliveryRequestStatus deliveryRequestStatus);
    DeliveryRequest getDeliveryRequestById(Long deliveryRequestId);
}
