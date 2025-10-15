package com.shyam_chindham.zomato_clone.services.impl;

import com.shyam_chindham.zomato_clone.entities.Customer;
import com.shyam_chindham.zomato_clone.entities.CustomerOrder;
import com.shyam_chindham.zomato_clone.entities.OrderRequest;
import com.shyam_chindham.zomato_clone.entities.enums.CustomerOrderStatus;
import com.shyam_chindham.zomato_clone.exceptions.ResourceNotFoundException;
import com.shyam_chindham.zomato_clone.repositories.CustomerOrderRepository;
import com.shyam_chindham.zomato_clone.services.CustomerOrderService;
import com.shyam_chindham.zomato_clone.utils.OtpGenerator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CustomerOrderServiceImpl implements CustomerOrderService {
    private  CustomerOrderRepository customerOrderRepository;
    private  ModelMapper modelMapper;
    @Override
    public CustomerOrder createCustomerOrder(OrderRequest orderRequest, Integer estimatedPreparationTime) {
        CustomerOrder customerOrder = CustomerOrder.builder()
                .customerOrderStatus(CustomerOrderStatus.PREPARING)
                .estimatedPreparationTime(estimatedPreparationTime)
                .orderAcceptedTime(LocalDateTime.now())
                .otp(OtpGenerator.generateRandomOtp())
                .totalAmount(orderRequest.getCart().getTotalAmount())
                .build();
        modelMapper.map(orderRequest,customerOrder);
        return customerOrderRepository.save(customerOrder);
    }

    @Override
    public CustomerOrder updateCustomerOrderStatus(CustomerOrder customerOrder, CustomerOrderStatus customerOrderStatus) {
        customerOrder.setCustomerOrderStatus(customerOrderStatus);
        return customerOrderRepository.save(customerOrder);
    }

    @Override
    public CustomerOrder getCustomerOrderById(Long customerOrderId) {
        return customerOrderRepository.findById(customerOrderId).orElseThrow(
                ()->new ResourceNotFoundException("Customer Order not found by id:"+customerOrderId)
        );
    }

    @Override
    public Page<CustomerOrder> getAllCustomerOrders(Customer customer, PageRequest pageRequest) {
        return customerOrderRepository.findByCustomer(customer,pageRequest);
    }
}
