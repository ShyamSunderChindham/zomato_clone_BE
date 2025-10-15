package com.shyam_chindham.zomato_clone.services.impl;

import com.shyam_chindham.zomato_clone.entities.Wallet;
import com.shyam_chindham.zomato_clone.entities.WalletTransaction;
import com.shyam_chindham.zomato_clone.repositories.WalletTransactionRepository;
import com.shyam_chindham.zomato_clone.services.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletTransactionServiceImpl implements WalletTransactionService {
    private  WalletTransactionRepository walletTransactionRepository;

    private  ModelMapper modelMapper;

    @Override
    public void createNewWalletTransaction(WalletTransaction walletTransaction) {
        walletTransactionRepository.save(walletTransaction);
    }

    @Override
    public Page<WalletTransaction> getAllWalletTransactionsByWallet(Wallet wallet, PageRequest pageRequest) {
        return walletTransactionRepository.findByWallet(wallet,pageRequest);
    }
}
