package com.shyam_chindham.zomato_clone.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer deliveryRating;
    private String deliveryReview;
    private Integer restaurantRating;
    private String restaurantReview;
    private String customerReview;
    private Integer customerRating;

    @OneToOne
    private CustomerOrder customerOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    private DeliveryPartner deliveryPartner;
}

