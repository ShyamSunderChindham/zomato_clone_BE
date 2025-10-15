package com.shyam_chindham.zomato_clone.services;

import com.shyam_chindham.zomato_clone.dtos.*;
import com.shyam_chindham.zomato_clone.entities.DeliveryPartner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface DeliveryPartnerService {

    ConfirmedDeliveryDto acceptDeliveryRequest(Long deliveryRequestId);
    ConfirmedDeliveryDto cancelConfirmedDelivery(Long confirmedDeliveryId);
    ConfirmedDeliveryDto completeConfirmedDeliveryOrder(Long confirmedDeliveryId, String otp);
    DeliveryPartnerDto getMyProfile();
    Page<ConfirmedDeliveryDto> getAllMyConfirmedDeliveries(PageRequest pageRequest);
    Page<WalletTransactionDto> getAllMyWalletTransactions(PageRequest pageRequest);
    DeliveryPartner getCurrentDeliveryPartner();
    DeliveryPartner updateAvailability(DeliveryPartner deliveryPartner,Boolean availability);

    CustomerDto giveFeedbackToCustomer(CustomerFeedbackDto customerFeedbackDto, Long confirmedDeliveryId);

    DeliveryPartner createNewDeliveryPartner(DeliveryPartner createDeliveryPartner);

    DeliveryPartnerDto updateMyLocation(LocationDto locationDto);
}
