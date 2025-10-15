package com.shyam_chindham.zomato_clone.repositories;

import com.shyam_chindham.zomato_clone.entities.Customer;
import com.shyam_chindham.zomato_clone.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByUser(User user);
}
