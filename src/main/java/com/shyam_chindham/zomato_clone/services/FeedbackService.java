package com.shyam_chindham.zomato_clone.services;

import com.shyam_chindham.zomato_clone.dtos.CustomerDto;
import com.shyam_chindham.zomato_clone.dtos.DeliveryPartnerDto;
import com.shyam_chindham.zomato_clone.dtos.RestaurantDto;
import com.shyam_chindham.zomato_clone.entities.ConfirmedDelivery;
import com.shyam_chindham.zomato_clone.entities.CustomerOrder;
import com.shyam_chindham.zomato_clone.entities.Feedback;

public interface FeedbackService {

    void createNewFeedback(ConfirmedDelivery confirmedDelivery);
    DeliveryPartnerDto giveFeedbackToDeliveryPartner(Feedback feedback, Integer rating, String review);
    RestaurantDto giveFeedbackToRestaurant(Feedback feedback, Integer rating, String review);
    CustomerDto giveFeedbackToCustomer(Feedback feedback, Integer rating, String review);
    Feedback getFeedbackByCustomerOrder(CustomerOrder customerOrder);
}
