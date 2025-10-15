package com.shyam_chindham.zomato_clone.strategies;

import com.shyam_chindham.zomato_clone.strategies.impl.DeliveryChargesDefaultCalculationStrategy;
import com.shyam_chindham.zomato_clone.strategies.impl.DeliveryChargesSurgeCalculationStrategy;
import com.shyam_chindham.zomato_clone.strategies.impl.DeliveryPartnerMatchingHighestRatedDeliveryPartnerStrategy;
import com.shyam_chindham.zomato_clone.strategies.impl.DeliveryPartnerMatchingNearestDeliveryPartnerStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class CustomerOrderStrategyManager {
    private  DeliveryPartnerMatchingHighestRatedDeliveryPartnerStrategy highestRatedDeliveryPartnerStrategy;
    private  DeliveryPartnerMatchingNearestDeliveryPartnerStrategy nearestDeliveryPartnerStrategy;
    private  DeliveryChargesDefaultCalculationStrategy deliveryChargesDefaultCalculationStrategy;
    private  DeliveryChargesSurgeCalculationStrategy deliveryChargesSurgeCalculationStrategy;

    public DeliveryPartnerMatchingStrategy deliveryPartnerMatchingStrategy(Double customerRating){
        if(customerRating >= 4.8) {
            return highestRatedDeliveryPartnerStrategy;
        } else {
            return nearestDeliveryPartnerStrategy;
        }
    }

    public DeliveryChargesCalculationStrategy deliveryChargesCalculationStrategy(){
        LocalTime surgeStartTime = LocalTime.of(18,0);
        LocalTime surgeEndTime = LocalTime.of(23,0);
        LocalTime currentTime = LocalTime.now();

        boolean isSurgeTime = currentTime.isAfter(surgeStartTime) && currentTime.isBefore(surgeEndTime);
        if (isSurgeTime){
            return deliveryChargesSurgeCalculationStrategy;
        }else{
            return deliveryChargesDefaultCalculationStrategy;
        }
    }
}
