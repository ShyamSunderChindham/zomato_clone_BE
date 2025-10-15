package com.shyam_chindham.zomato_clone.repositories;

import com.shyam_chindham.zomato_clone.entities.User;
import com.shyam_chindham.zomato_clone.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet,Long> {
    Optional<Wallet> findByUser(User user);
}
