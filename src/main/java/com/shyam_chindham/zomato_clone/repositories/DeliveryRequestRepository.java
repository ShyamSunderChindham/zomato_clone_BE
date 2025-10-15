package com.shyam_chindham.zomato_clone.repositories;

import com.shyam_chindham.zomato_clone.entities.CustomerOrder;
import com.shyam_chindham.zomato_clone.entities.DeliveryRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryRequestRepository extends JpaRepository<DeliveryRequest,Long> {
    Optional<DeliveryRequest> findByCustomerOrder(CustomerOrder customerOrder);
}
