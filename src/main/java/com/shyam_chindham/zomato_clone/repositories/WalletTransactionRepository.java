package com.shyam_chindham.zomato_clone.repositories;

import com.shyam_chindham.zomato_clone.entities.Wallet;
import com.shyam_chindham.zomato_clone.entities.WalletTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction,Long> {
    Page<WalletTransaction> findByWallet(Wallet wallet, PageRequest pageRequest);
}
