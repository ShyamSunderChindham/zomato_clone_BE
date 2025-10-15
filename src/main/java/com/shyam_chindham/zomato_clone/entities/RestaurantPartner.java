package com.shyam_chindham.zomato_clone.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "restaurant_partner")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantPartner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private Long aadharNo;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne(mappedBy = "restaurantPartner")
    private Restaurant restaurant;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin addedBy;
}
