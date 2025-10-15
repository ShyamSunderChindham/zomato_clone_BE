package com.shyam_chindham.zomato_clone.services.impl;

import com.shyam_chindham.zomato_clone.entities.ConfirmedDelivery;
import com.shyam_chindham.zomato_clone.entities.CustomerOrder;
import com.shyam_chindham.zomato_clone.entities.Payment;
import com.shyam_chindham.zomato_clone.entities.enums.PaymentStatus;
import com.shyam_chindham.zomato_clone.exceptions.ResourceNotFoundException;
import com.shyam_chindham.zomato_clone.repositories.PaymentRepository;
import com.shyam_chindham.zomato_clone.services.PaymentService;
import com.shyam_chindham.zomato_clone.strategies.PaymentStrategyManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private  PaymentRepository paymentRepository;
    private  PaymentStrategyManager paymentStrategyManager;
    @Override
    public void processPayment(ConfirmedDelivery confirmedDelivery) {
        Payment payment = paymentRepository.findByCustomerOrder(confirmedDelivery.getDeliveryRequest().getCustomerOrder()).orElseThrow(
                ()-> new ResourceNotFoundException("Payment not found for customer order with id:"+ confirmedDelivery.getId())
        );
        paymentStrategyManager.paymentStrategy(payment.getPaymentMethod()).processPayment(payment,confirmedDelivery);
    }

    @Override
    public void createNewPayment(CustomerOrder customerOrder) {
        Payment payment = Payment.builder()
                .paymentMethod(customerOrder.getPaymentMethod())
                .paymentStatus(PaymentStatus.PENDING)
                .customerOrder(customerOrder)
                .amount(customerOrder.getGrandTotal())
                .build();
        paymentRepository.save(payment);
    }
}
