package com.shyam_chindham.zomato_clone.strategies;

import com.shyam_chindham.zomato_clone.entities.enums.PaymentMethod;
import com.shyam_chindham.zomato_clone.strategies.impl.CashPaymentStrategy;
import com.shyam_chindham.zomato_clone.strategies.impl.WalletPaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentStrategyManager {
    private  CashPaymentStrategy cashPaymentStrategy;
    private  WalletPaymentStrategy walletPaymentStrategy;

    public PaymentStrategy paymentStrategy(PaymentMethod paymentMethod){
        return switch (paymentMethod){
            case WALLET -> walletPaymentStrategy;
            case CASH -> cashPaymentStrategy;
        };
    }
}
