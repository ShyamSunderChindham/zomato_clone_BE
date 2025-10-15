package com.shyam_chindham.zomato_clone.entities;


import com.shyam_chindham.zomato_clone.entities.enums.TransactionMethod;
import com.shyam_chindham.zomato_clone.entities.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletTransaction {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private Double amount;

    private TransactionType transactionType;

    private TransactionMethod transactionMethod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private CustomerOrder customerOrder;

    private String transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Wallet wallet;

    @CreationTimestamp
    private LocalDateTime timeStamp;

}
