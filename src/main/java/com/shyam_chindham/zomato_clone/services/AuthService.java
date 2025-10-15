package com.shyam_chindham.zomato_clone.services;

import com.shyam_chindham.zomato_clone.dtos.*;

public interface AuthService {

    String[] login(String email,String password);

    UserDto signup(SignupDto signupDto);

    DeliveryPartnerDto onBoardDeliveryPartner(Long userId, OnBoardDeliveryPartnerDto onBoardDeliveryPartnerDto);

    RestaurantPartnerDto onBoardRestaurantPartner(Long userId, OnBoardRestaurantPartnerDto onBoardRestaurantPartnerDto);

    String refreshToken(String refreshToken);
}
