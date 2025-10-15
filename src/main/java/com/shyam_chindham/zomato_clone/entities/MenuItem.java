package com.shyam_chindham.zomato_clone.entities;

import com.shyam_chindham.zomato_clone.entities.enums.MenuItemType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Data
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;
    private Boolean isAvailable;
    @Enumerated(EnumType.STRING)
    private MenuItemType menuItemType;
    private Double rating;
    private Double price;
}
