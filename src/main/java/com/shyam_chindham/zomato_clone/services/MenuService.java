package com.shyam_chindham.zomato_clone.services;

import com.shyam_chindham.zomato_clone.entities.Menu;
import com.shyam_chindham.zomato_clone.entities.Restaurant;

import java.util.List;

public interface MenuService {
    Menu createNewMenuForRestaurant(Menu menu, Restaurant restaurant);
    Menu updateMenuActiveStatus(Boolean status,Menu menu);
    Menu getMenuById(Long menuId);
    List<Menu> getMenusByRestaurant(Restaurant restaurant);
    Menu updateMenuOfRestaurant(Menu menu, Long menuId);
}
