package com.shyam_chindham.zomato_clone.services.impl;

import com.shyam_chindham.zomato_clone.entities.CustomerOrder;
import com.shyam_chindham.zomato_clone.entities.User;
import com.shyam_chindham.zomato_clone.entities.Wallet;
import com.shyam_chindham.zomato_clone.entities.WalletTransaction;
import com.shyam_chindham.zomato_clone.entities.enums.TransactionMethod;
import com.shyam_chindham.zomato_clone.entities.enums.TransactionType;
import com.shyam_chindham.zomato_clone.exceptions.ResourceNotFoundException;
import com.shyam_chindham.zomato_clone.repositories.WalletRepository;
import com.shyam_chindham.zomato_clone.services.WalletService;
import com.shyam_chindham.zomato_clone.services.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private  WalletTransactionService walletTransactionService;
    private  WalletRepository walletRepository;
    @Override
    public Wallet addMoneyToWallet(User user, Double amount, String transactionId, CustomerOrder customerOrder, TransactionMethod transactionMethod) {
        Wallet wallet = findByUser(user);
        wallet.setBalance(wallet.getBalance()+amount);
        WalletTransaction walletTransaction = WalletTransaction.builder()
                .transactionId(transactionId)
                .customerOrder(customerOrder)
                .wallet(wallet)
                .transactionType(TransactionType.CREDIT)
                .transactionMethod(transactionMethod)
                .amount(amount)
                .build();

        walletTransactionService.createNewWalletTransaction(walletTransaction);

        return walletRepository.save(wallet);
    }

    @Override
    public Wallet deductMoneyFromWallet(User user, Double amount, String transactionId, CustomerOrder customerOrder, TransactionMethod transactionMethod) {
        Wallet wallet = findByUser(user);
        wallet.setBalance(wallet.getBalance()-amount);
        WalletTransaction walletTransaction = WalletTransaction.builder()
                .transactionId(transactionId)
                .customerOrder(customerOrder)
                .wallet(wallet)
                .transactionType(TransactionType.DEBIT)
                .transactionMethod(transactionMethod)
                .amount(amount)
                .build();

        walletTransactionService.createNewWalletTransaction(walletTransaction);

        return walletRepository.save(wallet);
    }

    @Override
    public void withDrawAllMyMoneyFromWallet() {

    }

    @Override
    public Wallet findWalletById(Long walletId) {
        return null;
    }

    @Override
    public Wallet createNewWallet(User user) {
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet findByUser(User user) {
        return walletRepository.findByUser(user).orElseThrow(
                ()-> new ResourceNotFoundException("Wallet not found for user with id:"+user.getId())
        );
    }
}
