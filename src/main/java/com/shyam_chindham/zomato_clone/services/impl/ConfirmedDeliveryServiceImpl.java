package com.shyam_chindham.zomato_clone.services.impl;

import com.shyam_chindham.zomato_clone.dtos.ConfirmedDeliveryDto;
import com.shyam_chindham.zomato_clone.entities.ConfirmedDelivery;
import com.shyam_chindham.zomato_clone.entities.DeliveryPartner;
import com.shyam_chindham.zomato_clone.entities.DeliveryRequest;
import com.shyam_chindham.zomato_clone.entities.enums.ConfirmedDeliveryStatus;
import com.shyam_chindham.zomato_clone.exceptions.ResourceNotFoundException;
import com.shyam_chindham.zomato_clone.repositories.ConfirmedDeliveryRepository;
import com.shyam_chindham.zomato_clone.services.ConfirmedDeliveryService;
import com.shyam_chindham.zomato_clone.utils.Constants;
import com.shyam_chindham.zomato_clone.utils.OtpGenerator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfirmedDeliveryServiceImpl implements ConfirmedDeliveryService {
    private  ConfirmedDeliveryRepository confirmedDeliveryRepository;
    private  ModelMapper modelMapper;
    @Override
    public ConfirmedDeliveryDto createConfirmedDelivery(DeliveryRequest deliveryRequest, DeliveryPartner deliveryPartner) {

        ConfirmedDelivery confirmedDelivery = ConfirmedDelivery.builder()
                .confirmedDeliveryStatus(ConfirmedDeliveryStatus.ACCEPTED)
                .deliveryPartner(deliveryPartner)
                .deliveryRequest(deliveryRequest)
                .pickUpAddress(deliveryRequest.getPickUpAddress())
                .dropOffAddress(deliveryRequest.getDropOffAddress())
                .pickUpOtp(OtpGenerator.generateRandomOtp())
                .grandTotal(deliveryRequest.getGrandTotal())
                .distance(deliveryRequest.getDistance())
                .estimatedTime(deliveryRequest.getEstimatedPreparationTime()+estimatedDeliveryTime(deliveryRequest.getDistance())) //TODO - calculate estimated time
                .build();
        ConfirmedDelivery savedConfirmedDelivery=confirmedDeliveryRepository.save(confirmedDelivery);
        return modelMapper.map(savedConfirmedDelivery,ConfirmedDeliveryDto.class);
    }

    @Override
    public ConfirmedDelivery updateConfirmedDeliveryStatus(ConfirmedDelivery confirmedDelivery, ConfirmedDeliveryStatus confirmedDeliveryStatus) {
        confirmedDelivery.setConfirmedDeliveryStatus(confirmedDeliveryStatus);
        return confirmedDeliveryRepository.save(confirmedDelivery);
    }

    @Override
    public ConfirmedDelivery getConfirmedDeliveryById(Long confirmedDeliveryId) {
        return confirmedDeliveryRepository.findById(confirmedDeliveryId).orElseThrow(
                ()->new ResourceNotFoundException("Confirmed Delivery not found by id:"+confirmedDeliveryId)
        );
    }

    @Override
    public Page<ConfirmedDelivery> getConfirmedDeliveriesByDeliveryPartner(DeliveryPartner deliveryPartner, PageRequest pageRequest) {
        return confirmedDeliveryRepository.findByDeliveryPartner(deliveryPartner,pageRequest);
    }

    private Integer estimatedDeliveryTime(Double distance) {
        double averageSpeed = Constants.AVG_SPEED; // Average speed in km/h
        double baseTimeInMinutes = (distance / averageSpeed) * 60;
        int totalEstimatedTime = (int) Math.ceil(baseTimeInMinutes);
        return totalEstimatedTime;
    }
}
