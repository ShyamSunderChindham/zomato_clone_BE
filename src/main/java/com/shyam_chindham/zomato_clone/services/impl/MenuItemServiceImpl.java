package com.shyam_chindham.zomato_clone.services.impl;

import com.shyam_chindham.zomato_clone.entities.Menu;
import com.shyam_chindham.zomato_clone.entities.MenuItem;
import com.shyam_chindham.zomato_clone.exceptions.ResourceNotFoundException;
import com.shyam_chindham.zomato_clone.repositories.MenuItemRepository;
import com.shyam_chindham.zomato_clone.services.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {
    private  MenuItemRepository menuItemRepository;

    @Override
    public MenuItem createMenuItemForMenu(MenuItem menuItem, Menu menu) {
        menuItem.setMenu(menu);
        menuItem.setRating(0.0);
        return menuItemRepository.save(menuItem);
    }

    @Override
    public MenuItem updateMenuItemForMenu(MenuItem menuItem, Menu menu) {
        menuItem.setMenu(menu);
        return menuItemRepository.save(menuItem);
    }

    @Override
    public MenuItem updateMenuItemAvailabilityStatus(Boolean availableStatus, MenuItem menuItem) {
        menuItem.setIsAvailable(availableStatus);
        return menuItemRepository.save(menuItem);
    }



    @Override
    public MenuItem getMenuItemById(Long menuItemId) {
        return menuItemRepository.findById(menuItemId).orElseThrow(
                ()-> new ResourceNotFoundException("MenuItem not found by id:"+menuItemId)
        );
    }
}
