package com.shyam_chindham.zomato_clone.repositories;

import com.shyam_chindham.zomato_clone.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
}
