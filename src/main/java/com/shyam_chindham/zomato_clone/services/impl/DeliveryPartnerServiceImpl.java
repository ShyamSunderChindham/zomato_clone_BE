package com.shyam_chindham.zomato_clone.services.impl;


import com.shyam_chindham.zomato_clone.dtos.*;
import com.shyam_chindham.zomato_clone.entities.*;
import com.shyam_chindham.zomato_clone.entities.enums.ConfirmedDeliveryStatus;
import com.shyam_chindham.zomato_clone.entities.enums.CustomerOrderStatus;
import com.shyam_chindham.zomato_clone.entities.enums.DeliveryRequestStatus;
import com.shyam_chindham.zomato_clone.entities.enums.OrderRequestStatus;
import com.shyam_chindham.zomato_clone.exceptions.AccessDeniedException;
import com.shyam_chindham.zomato_clone.exceptions.BadRequestException;
import com.shyam_chindham.zomato_clone.exceptions.ResourceNotFoundException;
import com.shyam_chindham.zomato_clone.repositories.DeliveryPartnerRepository;
import com.shyam_chindham.zomato_clone.repositories.DeliveryRequestRepository;
import com.shyam_chindham.zomato_clone.repositories.UserRepository;
import com.shyam_chindham.zomato_clone.services.*;
import com.shyam_chindham.zomato_clone.utils.GeometryUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DeliveryPartnerServiceImpl implements DeliveryPartnerService {
    private  ModelMapper modelMapper;
    private  DeliveryRequestService deliveryRequestService;
    private  DeliveryPartnerRepository deliveryPartnerRepository;
    private  UserRepository userRepository;
    private  DeliveryRequestRepository deliveryRequestRepository;
    private  ConfirmedDeliveryService confirmedDeliveryService;
    private  CustomerOrderService customerOrderService;
    private  OrderRequestService orderRequestService;
    private  PaymentService paymentService;
    private  FeedbackService feedbackService;
    private  WalletService walletService;
    private  WalletTransactionService walletTransactionService;

    @Override
    @Transactional
    public ConfirmedDeliveryDto acceptDeliveryRequest(Long deliveryRequestId) {
        DeliveryPartner deliveryPartner = getCurrentDeliveryPartner();
        //Check if delivery person status is available to accept the delivery request
        if(!deliveryPartner.getIsAvailable()){
            throw new BadRequestException("Cannot accept delivery, delivery person status is not available");
        }
        DeliveryRequest deliveryRequest = deliveryRequestService.getDeliveryRequestById(deliveryRequestId);
        //Allow to accept delivery request only if delivery request status is pending
        if(!deliveryRequest.getDeliveryRequestStatus().equals(DeliveryRequestStatus.PENDING)){
            throw new BadRequestException("Delivery request can't be accepted its status is:"+deliveryRequest.getDeliveryRequestStatus().name());
        }
        //Update Delivery request status to Accepted
        DeliveryRequest updatedDeliveryRequest = deliveryRequestService.updateDeliveryRequestStatus(deliveryRequest,DeliveryRequestStatus.ACCEPTED);
        //Set delivery person availability to false
        DeliveryPartner updatedDeliveryPartner = updateAvailability(deliveryPartner,false);
        //Create Confirmed Delivery
        ConfirmedDeliveryDto confirmedDeliveryDto = confirmedDeliveryService.createConfirmedDelivery(updatedDeliveryRequest,updatedDeliveryPartner);
        //TODO-Send notification to restaurant owner and user with deliveryPartner details that delivery request is accepted by delivery person
        return confirmedDeliveryDto;
    }

    @Override
    @Transactional
    public ConfirmedDeliveryDto cancelConfirmedDelivery(Long confirmedDeliveryId) {
        ConfirmedDelivery confirmedDelivery = confirmedDeliveryService.getConfirmedDeliveryById(confirmedDeliveryId);
        DeliveryPartner deliveryPartner = getCurrentDeliveryPartner();
        //Check if this confirmed delivery belongs to the current driver
        if(!deliveryPartner.getId().equals(confirmedDelivery.getDeliveryPartner().getId())){
            throw new AccessDeniedException("Confirmed delivery having id:"+confirmedDeliveryId+" does not belong to current driver with id:"+deliveryPartner.getId());
        }
        //Cancel confirmed delivery ->(Only when order status was accepted and not out for delivery) on cancel update delivery request to pending for the order
        if(!confirmedDelivery.getConfirmedDeliveryStatus().equals(ConfirmedDeliveryStatus.ACCEPTED)){
            throw new BadRequestException("Cannot cancel delivery it's status is:"+confirmedDelivery.getConfirmedDeliveryStatus());
        }
        //Update confirmed delivery status to cancelled
        ConfirmedDelivery updateConfirmedDelivery=confirmedDeliveryService.updateConfirmedDeliveryStatus(confirmedDelivery,ConfirmedDeliveryStatus.CANCELLED);
        //Update delivery request status back to pending from accepted
        deliveryRequestService.updateDeliveryRequestStatus(confirmedDelivery.getDeliveryRequest(),DeliveryRequestStatus.PENDING);
        //Set delivery perseon availability
        updateAvailability(deliveryPartner,true);
        return modelMapper.map(updateConfirmedDelivery, ConfirmedDeliveryDto.class);
    }

    @Override
    @Transactional
    public ConfirmedDeliveryDto completeConfirmedDeliveryOrder(Long confirmedDeliveryId, String otp) {
        ConfirmedDelivery confirmedDelivery = confirmedDeliveryService.getConfirmedDeliveryById(confirmedDeliveryId);
        DeliveryPartner deliveryPartner = getCurrentDeliveryPartner();
        //Check if this confirmed delivery belongs to the current driver
        if(!deliveryPartner.getId().equals(confirmedDelivery.getDeliveryPartner().getId())){
            throw new AccessDeniedException("Confirmed delivery having id:"+confirmedDeliveryId+" does not belong to current driver with id:"+deliveryPartner.getId());
        }
        //Status of confirmed delivery has to be OUT_FOR_DELIVERY
        if(!confirmedDelivery.getConfirmedDeliveryStatus().equals(ConfirmedDeliveryStatus.OUT_FOR_DELIVERY)){
            throw new BadRequestException("Cannot complete delivery it's status is:"+confirmedDelivery.getConfirmedDeliveryStatus());
        }
        //Check if otp is correct to complete delivery
        CustomerOrder customerOrder = customerOrderService.getCustomerOrderById(confirmedDelivery.getDeliveryRequest().getCustomerOrder().getId());
        if(!otp.equals(customerOrder.getOtp())){
            throw new BadRequestException("Otp is Invalid");
        }

        //Complete delivery by updating statuses
        //Update order request status
        OrderRequest orderRequest = orderRequestService.getOrderRequestByCart(confirmedDelivery.getDeliveryRequest().getCustomerOrder().getCart());
        OrderRequest updatedOrderRequest = orderRequestService.updateOrderRequestStatus(orderRequest, OrderRequestStatus.FULLFILLED);
        //Update customer order status
        CustomerOrder updatedCustomerOrder = customerOrderService.updateCustomerOrderStatus(customerOrder, CustomerOrderStatus.DELIVERED);
        //Update Delivery request status, also set updatedCustomerOrder to delivery request
        DeliveryRequest deliveryRequest = deliveryRequestService.getDeliveryRequestById(confirmedDelivery.getDeliveryRequest().getId());
        DeliveryRequest updateDeliveryRequest = deliveryRequestService.updateDeliveryRequestStatus(deliveryRequest,DeliveryRequestStatus.FULLFILLED);
        //Update Confirmed delivery status
        confirmedDelivery.setEstimatedTime(0);
        confirmedDelivery.setDeliveryCompleteTime(LocalDateTime.now());
        confirmedDelivery.setDeliveryRequest(updateDeliveryRequest);
        ConfirmedDelivery updatedConfirmedDelivery=confirmedDeliveryService.updateConfirmedDeliveryStatus(confirmedDelivery,ConfirmedDeliveryStatus.COMPLETED);
        //Process payment for the customer order
        paymentService.processPayment(updatedConfirmedDelivery);
        //Set driver availability status to available
        updateAvailability(deliveryPartner,Boolean.TRUE);
        //Create new Review associated with this customer order
        feedbackService.createNewFeedback(updatedConfirmedDelivery);
        //TODO - send notification or email to restaurant that delivery is Completed
        return modelMapper.map(updatedConfirmedDelivery, ConfirmedDeliveryDto.class);
    }

    @Override
    public DeliveryPartnerDto getMyProfile() {
        DeliveryPartner deliveryPartner = getCurrentDeliveryPartner();
        return modelMapper.map(deliveryPartner, DeliveryPartnerDto.class);
    }

    @Override
    public Page<ConfirmedDeliveryDto> getAllMyConfirmedDeliveries(PageRequest pageRequest) {
        DeliveryPartner deliveryPartner = getCurrentDeliveryPartner();
        return confirmedDeliveryService.getConfirmedDeliveriesByDeliveryPartner(deliveryPartner,pageRequest).map(
                confirmedDelivery -> modelMapper.map(confirmedDelivery, ConfirmedDeliveryDto.class)
        );
    }

    @Override
    public Page<WalletTransactionDto> getAllMyWalletTransactions(PageRequest pageRequest) {
        DeliveryPartner deliveryPartner = getCurrentDeliveryPartner();
        Wallet wallet = walletService.findByUser(deliveryPartner.getUser());
        return walletTransactionService.getAllWalletTransactionsByWallet(wallet,pageRequest).map(
                walletTransaction -> modelMapper.map(walletTransaction, WalletTransactionDto.class)
        );
    }

    @Override
    public DeliveryPartner getCurrentDeliveryPartner() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return deliveryPartnerRepository.findByUser(user).orElseThrow(
                ()-> new ResourceNotFoundException("Delivery Person not found by user id:"+user.getId())
        );
    }

    @Override
    public DeliveryPartner updateAvailability(DeliveryPartner deliveryPartner, Boolean availability) {
        deliveryPartner.setIsAvailable(availability);
        return deliveryPartnerRepository.save(deliveryPartner);
    }

    @Override
    public CustomerDto giveFeedbackToCustomer(CustomerFeedbackDto customerFeedbackDto, Long confirmedDeliveryId) {
        ConfirmedDelivery confirmedDelivery = confirmedDeliveryService.getConfirmedDeliveryById(confirmedDeliveryId);
        DeliveryPartner deliveryPartner = getCurrentDeliveryPartner();
        //Check delivery person is associated with confirmed delivery
        if(!confirmedDelivery.getDeliveryPartner().equals(deliveryPartner)){
            throw new AccessDeniedException("Cannot review and rate Customer, confirmed delivery with id:"+confirmedDelivery.getId()+" does not belong to delivery person with id:"+deliveryPartner.getId());
        }
        //Allow to review and rate customer if confirmed delivery status is completed
        if(!confirmedDelivery.getConfirmedDeliveryStatus().equals(ConfirmedDeliveryStatus.COMPLETED)){
            throw new BadRequestException("cannot review and rate customer, the confirmed delivery status is:"+confirmedDelivery.getConfirmedDeliveryStatus()+" it has to be completed");
        }
        Feedback feedback = feedbackService.getFeedbackByCustomerOrder(confirmedDelivery.getDeliveryRequest().getCustomerOrder());
        return feedbackService.giveFeedbackToCustomer(feedback,customerFeedbackDto.getRating(),customerFeedbackDto.getReview());
    }

    @Override
    public DeliveryPartner createNewDeliveryPartner(DeliveryPartner createDeliveryPartner) {
        return deliveryPartnerRepository.save(createDeliveryPartner);
    }

    @Override
    public DeliveryPartnerDto updateMyLocation(LocationDto locationDto) {
        DeliveryPartner deliveryPartner = getCurrentDeliveryPartner();
        deliveryPartner.setCurrentLocation(GeometryUtil.createPoint(locationDto.getAddress()));
        return modelMapper.map(deliveryPartnerRepository.save(deliveryPartner),DeliveryPartnerDto.class);
    }
}
