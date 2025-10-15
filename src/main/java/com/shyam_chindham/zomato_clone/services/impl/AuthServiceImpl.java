package com.shyam_chindham.zomato_clone.services.impl;

import com.shyam_chindham.zomato_clone.dtos.*;
import com.shyam_chindham.zomato_clone.entities.DeliveryPartner;
import com.shyam_chindham.zomato_clone.entities.RestaurantPartner;
import com.shyam_chindham.zomato_clone.entities.User;
import com.shyam_chindham.zomato_clone.entities.enums.Role;
import com.shyam_chindham.zomato_clone.exceptions.ResourceNotFoundException;
import com.shyam_chindham.zomato_clone.exceptions.RuntimeConflictException;
import com.shyam_chindham.zomato_clone.repositories.UserRepository;
import com.shyam_chindham.zomato_clone.securrity.JWTService;
import com.shyam_chindham.zomato_clone.services.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private  ModelMapper modelMapper;
    private  CustomerService customerService;
    private  WalletService walletService;
    private  UserRepository userRepository;
    private  PasswordEncoder passwordEncoder;
    private  AuthenticationManager authenticationManager;
    private  JWTService jwtService;
    private  DeliveryPartnerService deliveryPartnerService;
    private  RestaurantPartnerService restaurantPartnerService;

    @Override
    public String[] login(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        User user = (User) authentication.getPrincipal();

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new String[]{accessToken, refreshToken};
    }

    @Override
    public UserDto signup(SignupDto signupDto) {
        User user = userRepository.findByEmail(signupDto.getEmail()).orElse(null);
        if(user != null)
            throw new RuntimeConflictException("Cannot signup, User already exists with email "+signupDto.getEmail());

        User mappedUser = modelMapper.map(signupDto, User.class);
        mappedUser.setRoles(Set.of(Role.CUSTOMER));
        mappedUser.setPassword(passwordEncoder.encode(mappedUser.getPassword()));
        User savedUser = userRepository.save(mappedUser);

        //create user related entities
        customerService.createNewCustomer(savedUser);
        walletService.createNewWallet(savedUser);

        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public DeliveryPartnerDto onBoardDeliveryPartner(Long userId, OnBoardDeliveryPartnerDto onBoardDeliveryPartnerDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id "+userId));

        if(user.getRoles().contains(Role.DELIVERY_PARTNER))
            throw new RuntimeConflictException("User with id "+userId+" is already a Delivery Person");
        user.getRoles().add(Role.DELIVERY_PARTNER);
        User savedUser=userRepository.save(user);
        DeliveryPartner createDeliveryPartner = DeliveryPartner.builder()
                .user(savedUser)
                .rating(0.0)
                .vehicleId(onBoardDeliveryPartnerDto.getVehicleId())
                .aadharNo(onBoardDeliveryPartnerDto.getAadharNo())
                .isAvailable(true)
                .build();
        DeliveryPartner savedDeliveryPartner = deliveryPartnerService.createNewDeliveryPartner(createDeliveryPartner);
        return modelMapper.map(savedDeliveryPartner, DeliveryPartnerDto.class);
    }

    @Override
    public RestaurantPartnerDto onBoardRestaurantPartner(Long userId, OnBoardRestaurantPartnerDto onBoardRestaurantPartnerDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id "+userId));

        if(user.getRoles().contains(Role.RESTAURANT_PARTNER))
            throw new RuntimeConflictException("User with id "+userId+" is already a Restaurant Owner");
        user.getRoles().add(Role.RESTAURANT_PARTNER);
        User savedUser = userRepository.save(user);
        RestaurantPartner createRestaurantPartner = RestaurantPartner.builder()
                .user(savedUser)
                .aadharNo(onBoardRestaurantPartnerDto.getAadharNo())
                .build();
        RestaurantPartner savedRestaurantPartner = restaurantPartnerService.createNewRestaurantPartner(createRestaurantPartner);
        return modelMapper.map(savedRestaurantPartner, RestaurantPartnerDto.class);
    }

    @Override
    public String refreshToken(String refreshToken) {
        Long userId = jwtService.getUserIdFromToken(refreshToken);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found " +
                "with id: "+userId));

        return jwtService.generateAccessToken(user);
    }
}
