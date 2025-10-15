package com.shyam_chindham.zomato_clone.services.impl;

import com.shyam_chindham.zomato_clone.dtos.CustomerDto;
import com.shyam_chindham.zomato_clone.dtos.DeliveryPartnerDto;
import com.shyam_chindham.zomato_clone.dtos.RestaurantDto;
import com.shyam_chindham.zomato_clone.entities.*;
import com.shyam_chindham.zomato_clone.exceptions.ResourceNotFoundException;
import com.shyam_chindham.zomato_clone.repositories.*;
import com.shyam_chindham.zomato_clone.services.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private  FeedbackRepository feedbackRepository;
    private  MenuItemFeedbackRepository menuItemFeedbackRepository;
    private  ModelMapper modelMapper;
    private  DeliveryPartnerRepository deliveryPartnerRepository;
    private  CustomerRepository customerRepository;
    private  RestaurantRepository restaurantRepository;
    @Override
    public void createNewFeedback(ConfirmedDelivery confirmedDelivery) {
        CustomerOrder customerOrder = confirmedDelivery.getDeliveryRequest().getCustomerOrder();
        Customer customer = customerOrder.getCustomer();
        Restaurant restaurant = customerOrder.getRestaurant();
        DeliveryPartner deliveryPartner = confirmedDelivery.getDeliveryPartner();

        Feedback feedback = Feedback.builder()
                .customer(customer)
                .restaurant(restaurant)
                .deliveryPartner(deliveryPartner)
                .customerOrder(customerOrder)
                .build();
        Feedback savedReview= feedbackRepository.save(feedback);

        List<MenuItemFeedback> menuItemFeedbacks=customerOrder.getCart().getCartItems().stream()
                .map(
                        cartItem -> MenuItemFeedback.builder()
                                .menuItem(cartItem.getMenuItem())
                                .feedback(savedReview)
                                .build()
                ).collect(Collectors.toList());
        menuItemFeedbackRepository.saveAll(menuItemFeedbacks);
    }

    @Override
    public DeliveryPartnerDto giveFeedbackToDeliveryPartner(Feedback feedback, Integer rating, String review) {
        feedback.setDeliveryReview(review);
        feedback.setDeliveryRating(rating);
        Feedback savedFeedback = feedbackRepository.save(feedback);
        DeliveryPartner deliveryPartner = savedFeedback.getDeliveryPartner();
        //Update delivery person rating
        Double newRating =feedbackRepository.findByDeliveryPartner(deliveryPartner).stream()
                .mapToDouble(Feedback::getDeliveryRating)
                .average().orElse(0.0);

        deliveryPartner.setRating(newRating);
        DeliveryPartner savedDeliveryPartner = deliveryPartnerRepository.save(deliveryPartner);
        return modelMapper.map(savedDeliveryPartner, DeliveryPartnerDto.class);
    }

    @Override
    public RestaurantDto giveFeedbackToRestaurant(Feedback feedback, Integer rating, String review) {
        feedback.setRestaurantReview(review);
        feedback.setRestaurantRating(rating);
        Feedback savedFeedback= feedbackRepository.save(feedback);
        Restaurant restaurant = savedFeedback.getRestaurant();
        //Update restaurant rating
        Double newRating =feedbackRepository.findByRestaurant(restaurant).stream()
                .mapToDouble(Feedback::getRestaurantRating)
                .average().orElse(0.0);

        restaurant.setRating(newRating);
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        return modelMapper.map(savedRestaurant, RestaurantDto.class);
    }

    @Override
    public CustomerDto giveFeedbackToCustomer(Feedback feedback, Integer rating, String review) {
        feedback.setCustomerReview(review);
        feedback.setCustomerRating(rating);
        Feedback savedFeedback= feedbackRepository.save(feedback);
        Customer customer = savedFeedback.getCustomer();
        //Update customer rating
        Double newRating =feedbackRepository.findByCustomer(customer).stream()
                .mapToDouble(Feedback::getCustomerRating)
                .average().orElse(0.0);

        customer.setRating(newRating);
        Customer savedCustomer=customerRepository.save(customer);
        return modelMapper.map(savedCustomer, CustomerDto.class);
    }

    @Override
    public Feedback getFeedbackByCustomerOrder(CustomerOrder customerOrder) {
        return feedbackRepository.findByCustomerOrder(customerOrder).orElseThrow(
                ()-> new ResourceNotFoundException("Review and Rating not found for for customer order having id:"+customerOrder.getId())
        );
    }
}
