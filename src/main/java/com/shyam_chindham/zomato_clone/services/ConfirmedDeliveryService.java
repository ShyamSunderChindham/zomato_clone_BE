package com.shyam_chindham.zomato_clone.services;

import com.shyam_chindham.zomato_clone.dtos.ConfirmedDeliveryDto;
import com.shyam_chindham.zomato_clone.entities.ConfirmedDelivery;
import com.shyam_chindham.zomato_clone.entities.DeliveryPartner;
import com.shyam_chindham.zomato_clone.entities.DeliveryRequest;
import com.shyam_chindham.zomato_clone.entities.enums.ConfirmedDeliveryStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ConfirmedDeliveryService {
    ConfirmedDeliveryDto createConfirmedDelivery(DeliveryRequest deliveryRequest, DeliveryPartner deliveryPartner);
    ConfirmedDelivery updateConfirmedDeliveryStatus(ConfirmedDelivery confirmedDelivery,ConfirmedDeliveryStatus confirmedDeliveryStatus);
    ConfirmedDelivery getConfirmedDeliveryById(Long confirmedDeliveryId);

    Page<ConfirmedDelivery> getConfirmedDeliveriesByDeliveryPartner(DeliveryPartner deliveryPartner, PageRequest pageRequest);
}
