package com.shyam_chindham.zomato_clone.services;

import com.shyam_chindham.zomato_clone.entities.Customer;
import com.shyam_chindham.zomato_clone.entities.CustomerOrder;
import com.shyam_chindham.zomato_clone.entities.OrderRequest;
import com.shyam_chindham.zomato_clone.entities.enums.CustomerOrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface CustomerOrderService {
    CustomerOrder createCustomerOrder(OrderRequest orderRequest,Integer estimatedPreparationTime);
    CustomerOrder updateCustomerOrderStatus(CustomerOrder customerOrder, CustomerOrderStatus customerOrderStatus);
    CustomerOrder getCustomerOrderById(Long customerOrderId);

    Page<CustomerOrder> getAllCustomerOrders(Customer customer, PageRequest pageRequest);
}
