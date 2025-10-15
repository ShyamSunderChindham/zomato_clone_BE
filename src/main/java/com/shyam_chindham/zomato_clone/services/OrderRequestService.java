package com.shyam_chindham.zomato_clone.services;

import com.shyam_chindham.zomato_clone.dtos.OrderRequestDto;
import com.shyam_chindham.zomato_clone.entities.Cart;
import com.shyam_chindham.zomato_clone.entities.OrderRequest;
import com.shyam_chindham.zomato_clone.entities.enums.OrderRequestStatus;

public interface OrderRequestService {
    OrderRequestDto createOrderRequest(OrderRequest orderRequest);
    OrderRequest updateOrderRequestStatus(OrderRequest orderRequest,OrderRequestStatus orderRequestStatus);
    OrderRequest getOrderRequestById(Long orderRequestId);
    OrderRequest getOrderRequestByCart(Cart cart);
}
