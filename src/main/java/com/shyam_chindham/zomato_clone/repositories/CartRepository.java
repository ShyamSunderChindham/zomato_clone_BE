package com.shyam_chindham.zomato_clone.repositories;

import com.shyam_chindham.zomato_clone.entities.Cart;
import com.shyam_chindham.zomato_clone.entities.Customer;
import com.shyam_chindham.zomato_clone.entities.Restaurant;
import com.shyam_chindham.zomato_clone.entities.enums.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {
    // Method to find a Cart by Customer, Restaurant, and CartStatus
    Optional<Cart> findByCustomerAndRestaurantAndCartStatus(Customer customer, Restaurant restaurant, CartStatus cartStatus);
}
