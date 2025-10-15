package com.shyam_chindham.zomato_clone.services;

import com.shyam_chindham.zomato_clone.entities.Menu;
import com.shyam_chindham.zomato_clone.entities.MenuItem;

public interface MenuItemService {
    MenuItem createMenuItemForMenu(MenuItem menuItem, Menu menu);
    MenuItem updateMenuItemForMenu(MenuItem menuItem, Menu menu);
    MenuItem updateMenuItemAvailabilityStatus(Boolean availableStatus,MenuItem menuItem);
    MenuItem getMenuItemById(Long menuItemId);
}
