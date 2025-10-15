package com.shyam_chindham.zomato_clone.repositories;


import com.shyam_chindham.zomato_clone.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback,Long> {
    Optional<Feedback> findByCustomerOrder(CustomerOrder customerOrder);

    List<Feedback> findByDeliveryPartner(DeliveryPartner deliveryPartner);

    List<Feedback> findByRestaurant(Restaurant restaurant);

    List<Feedback> findByCustomer(Customer customer);
}
