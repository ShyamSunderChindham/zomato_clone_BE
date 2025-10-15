package com.shyam_chindham.zomato_clone.repositories;

import com.shyam_chindham.zomato_clone.entities.Cart;
import com.shyam_chindham.zomato_clone.entities.OrderRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRequestRepository extends JpaRepository<OrderRequest,Long> {
    Optional<OrderRequest> findByCart(Cart cart);
}
