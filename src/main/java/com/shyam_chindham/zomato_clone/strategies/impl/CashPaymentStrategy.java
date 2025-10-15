package com.shyam_chindham.zomato_clone.strategies.impl;

import com.shyam_chindham.zomato_clone.entities.*;
import com.shyam_chindham.zomato_clone.entities.enums.PaymentStatus;
import com.shyam_chindham.zomato_clone.entities.enums.TransactionMethod;
import com.shyam_chindham.zomato_clone.repositories.PaymentRepository;
import com.shyam_chindham.zomato_clone.services.WalletService;
import com.shyam_chindham.zomato_clone.strategies.PaymentStrategy;
import com.shyam_chindham.zomato_clone.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CashPaymentStrategy implements PaymentStrategy {
    private  WalletService walletService;
    private  PaymentRepository paymentRepository;
    @Override
    public void processPayment(Payment payment, ConfirmedDelivery confirmedDelivery) {

        DeliveryRequest deliveryRequest = confirmedDelivery.getDeliveryRequest();
        CustomerOrder customerOrder = deliveryRequest.getCustomerOrder();

        RestaurantPartner restaurantPartner = payment.getCustomerOrder().getRestaurant().getRestaurantPartner();
        DeliveryPartner deliveryPartner =  confirmedDelivery.getDeliveryPartner();
        //Deduct money from DeliveryPartner's wallet
        Double deliveryPartnerPay = calculateDeliveryPartnerPay(customerOrder.getTotalAmount(),customerOrder.getDeliveryCharges());
        Double amountToBeDeducted = confirmedDelivery.getGrandTotal()-deliveryPartnerPay;
        walletService.deductMoneyFromWallet(deliveryPartner.getUser(),amountToBeDeducted,null,customerOrder, TransactionMethod.ORDER);
        //Add money to Restaurant Owner's wallet
        Double amountToBeAdded=customerOrder.getTotalAmount()-(customerOrder.getTotalAmount()* Constants.COMMISSION_ON_ORDER);
        walletService.addMoneyToWallet(restaurantPartner.getUser(),amountToBeAdded,null,customerOrder,TransactionMethod.ORDER);
        //Update payment status
        payment.setPaymentTime(LocalDateTime.now());
        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);
    }

}
