package com.shyam_chindham.zomato_clone.services;

import com.shyam_chindham.zomato_clone.entities.Wallet;
import com.shyam_chindham.zomato_clone.entities.WalletTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface WalletTransactionService {
    void createNewWalletTransaction(WalletTransaction walletTransaction);
    Page<WalletTransaction> getAllWalletTransactionsByWallet(Wallet wallet, PageRequest pageRequest);
}
