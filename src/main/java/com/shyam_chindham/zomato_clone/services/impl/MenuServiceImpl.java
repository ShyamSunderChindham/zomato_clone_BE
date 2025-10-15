package com.shyam_chindham.zomato_clone.services.impl;

import com.shyam_chindham.zomato_clone.entities.Menu;
import com.shyam_chindham.zomato_clone.entities.Restaurant;
import com.shyam_chindham.zomato_clone.exceptions.ResourceNotFoundException;
import com.shyam_chindham.zomato_clone.repositories.MenuRepository;
import com.shyam_chindham.zomato_clone.services.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private  MenuRepository menuRepository;
    @Override
    public Menu createNewMenuForRestaurant(Menu menu, Restaurant restaurant) {
        menu.setRestaurant(restaurant);
        return menuRepository.save(menu);
    }

    @Override
    public Menu updateMenuActiveStatus(Boolean status,Menu menu) {
        menu.setIsActive(status);
        return menuRepository.save(menu);
    }

    @Override
    public Menu getMenuById(Long menuId) {
        return menuRepository.findById(menuId).orElseThrow(
                ()-> new ResourceNotFoundException("Menu not found by id:"+menuId)
        );
    }

    @Override
    public List<Menu> getMenusByRestaurant(Restaurant restaurant) {
        return menuRepository.findByRestaurant(restaurant);
    }

    @Override
    public Menu updateMenuOfRestaurant(Menu menu, Long menuId) {
        menu.setId(menuId);
        return menuRepository.save(menu);
    }
}
