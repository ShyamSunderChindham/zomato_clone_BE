package com.shyam_chindham.zomato_clone.entities;


import com.shyam_chindham.zomato_clone.entities.enums.CustomerOrderStatus;
import com.shyam_chindham.zomato_clone.entities.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;
    private Double totalAmount;
    private Double deliveryCharges;
    private Double platformFee;
    private Double grandTotal;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "restaurant_id")
    private Restaurant restaurant;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "customer_id")
    private Customer customer;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    //
    private Integer estimatedPreparationTime;
    //
    @Enumerated(EnumType.STRING)
    private CustomerOrderStatus customerOrderStatus;
    private String otp;
    //
    @CreationTimestamp
    private LocalDateTime orderAcceptedTime;
}
