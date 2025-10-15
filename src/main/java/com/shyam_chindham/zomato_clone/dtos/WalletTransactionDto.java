package com.shyam_chindham.zomato_clone.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shyam_chindham.zomato_clone.entities.enums.TransactionMethod;
import com.shyam_chindham.zomato_clone.entities.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletTransactionDto {

    private Long id;

    private Double amount;

    private TransactionType transactionType;

    private TransactionMethod transactionMethod;

    @JsonIgnore
    private CustomerOrderDto customerOrder;

    private String transactionId;

    private WalletDto wallet;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timeStamp;
}
