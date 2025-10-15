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
public class WalletPaymentStrategy implements PaymentStrategy {
    private  WalletService walletService;
    private  PaymentRepository paymentRepository;
    @Override
    public void processPayment(Payment payment, ConfirmedDelivery confirmedDelivery) {
        Customer customer = payment.getCustomerOrder().getCustomer();
        DeliveryRequest deliveryRequest = confirmedDelivery.getDeliveryRequest();
        CustomerOrder customerOrder = deliveryRequest.getCustomerOrder();

        RestaurantPartner restaurantPartner = payment.getCustomerOrder().getRestaurant().getRestaurantPartner();
        DeliveryPartner deliveryPartner =  confirmedDelivery.getDeliveryPartner();

        //Deduct money from customer's wallet
        Double grandTotal = customerOrder.getGrandTotal();
        walletService.deductMoneyFromWallet(customer.getUser(),grandTotal,null,customerOrder,TransactionMethod.ORDER);
        //Add money to delivery person's wallet
        Double amountToBeAdded = calculateDeliveryPartnerPay(customerOrder.getTotalAmount(),customerOrder.getDeliveryCharges());
        walletService.addMoneyToWallet(deliveryPartner.getUser(),amountToBeAdded,null,customerOrder, TransactionMethod.ORDER);
        //Add money to Restaurant Owner's wallet
        Double totalAmount=customerOrder.getTotalAmount()-(customerOrder.getTotalAmount()* Constants.COMMISSION_ON_ORDER);
        walletService.addMoneyToWallet(restaurantPartner.getUser(),totalAmount,null,customerOrder,TransactionMethod.ORDER);
        //Update payment status
        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        payment.setPaymentTime(LocalDateTime.now());
        paymentRepository.save(payment);
    }
}
