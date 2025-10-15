package com.shyam_chindham.zomato_clone.services;

import com.shyam_chindham.zomato_clone.entities.ConfirmedDelivery;
import com.shyam_chindham.zomato_clone.entities.CustomerOrder;

public interface PaymentService {
    void processPayment(ConfirmedDelivery confirmedDelivery);
    void createNewPayment(CustomerOrder customerOrder);
}
