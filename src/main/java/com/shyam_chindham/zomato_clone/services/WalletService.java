package com.shyam_chindham.zomato_clone.services;

import com.shyam_chindham.zomato_clone.entities.CustomerOrder;
import com.shyam_chindham.zomato_clone.entities.User;
import com.shyam_chindham.zomato_clone.entities.Wallet;
import com.shyam_chindham.zomato_clone.entities.enums.TransactionMethod;

public interface WalletService {
    Wallet addMoneyToWallet(User user, Double amount, String transactionId, CustomerOrder customerOrder, TransactionMethod transactionMethod);
    Wallet deductMoneyFromWallet(User user,Double amount,String transactionId,CustomerOrder customerOrder,TransactionMethod transactionMethod);
    void withDrawAllMyMoneyFromWallet();
    Wallet findWalletById(Long walletId);
    Wallet createNewWallet(User user);
    Wallet findByUser(User user);
}
