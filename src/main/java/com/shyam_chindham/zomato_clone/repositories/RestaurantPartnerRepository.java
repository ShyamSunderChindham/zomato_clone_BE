package com.shyam_chindham.zomato_clone.repositories;

import com.shyam_chindham.zomato_clone.entities.RestaurantPartner;
import com.shyam_chindham.zomato_clone.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantPartnerRepository extends JpaRepository<RestaurantPartner,Long> {
    Optional<RestaurantPartner> findByUser(User user);
}
