package com.shyam_chindham.zomato_clone.services.impl;

import com.shyam_chindham.zomato_clone.dtos.OrderRequestDto;
import com.shyam_chindham.zomato_clone.entities.Cart;
import com.shyam_chindham.zomato_clone.entities.OrderRequest;
import com.shyam_chindham.zomato_clone.entities.enums.OrderRequestStatus;
import com.shyam_chindham.zomato_clone.exceptions.ResourceNotFoundException;
import com.shyam_chindham.zomato_clone.repositories.OrderRequestRepository;
import com.shyam_chindham.zomato_clone.services.DistanceService;
import com.shyam_chindham.zomato_clone.services.OrderRequestService;
import com.shyam_chindham.zomato_clone.strategies.CustomerOrderStrategyManager;
import com.shyam_chindham.zomato_clone.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderRequestServiceImpl implements OrderRequestService {

    private  OrderRequestRepository orderRequestRepository;
    private  CustomerOrderStrategyManager customerOrderStrategyManager;
    private  DistanceService distanceService;
    private  ModelMapper modelMapper;

    @Override
    public OrderRequestDto createOrderRequest(OrderRequest orderRequest) {

        double totalAmount = orderRequest.getCart().getTotalAmount();
        Point source = orderRequest.getCustomer().getDeliveryAddress();
        Point destination = orderRequest.getRestaurant().getAddress();
        double distance = distanceService.calculateDistance(source, destination);
        double deliveryCharges = customerOrderStrategyManager.deliveryChargesCalculationStrategy().calculateDeliveryCharges(totalAmount,distance);
        //Create new order request with status pending and make status accepted after Restaurant owner accepts the order
        orderRequest.setOrderRequestStatus(OrderRequestStatus.PENDING);
        //Calculate the delivery charges and set, also set the Platform fees
        orderRequest.setDeliveryCharges(deliveryCharges);
        orderRequest.setPlatformFee(Constants.PLATFORM_FEE);
        //Set Grand total
        orderRequest.setGrandTotal(totalAmount+deliveryCharges+ Constants.PLATFORM_FEE);
        //Save Order request
        OrderRequest savedOrderRequest = orderRequestRepository.save(orderRequest);
        //TODO:-Notify Restaurant owner about order
        return modelMapper.map(savedOrderRequest,OrderRequestDto.class);
    }

    @Override
    public OrderRequest updateOrderRequestStatus(OrderRequest orderRequest, OrderRequestStatus orderRequestStatus) {
        orderRequest.setOrderRequestStatus(orderRequestStatus);
        return orderRequestRepository.save(orderRequest);
    }

    @Override
    public OrderRequest getOrderRequestById(Long orderRequestId) {
        return orderRequestRepository.findById(orderRequestId).orElseThrow(
                ()-> new ResourceNotFoundException("Order request not found by id:"+orderRequestId)
        );
    }

    @Override
    public OrderRequest getOrderRequestByCart(Cart cart) {
        return orderRequestRepository.findByCart(cart).orElseThrow(
                ()->new ResourceNotFoundException("Order request not found for cart with id:"+cart.getId())
        );
    }
}
