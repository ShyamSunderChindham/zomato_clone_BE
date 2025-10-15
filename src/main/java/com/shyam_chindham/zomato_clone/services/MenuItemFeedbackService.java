package com.shyam_chindham.zomato_clone.services;

import com.shyam_chindham.zomato_clone.dtos.MenuItemDto;
import com.shyam_chindham.zomato_clone.entities.Feedback;
import com.shyam_chindham.zomato_clone.entities.MenuItemFeedback;

import java.util.List;

public interface MenuItemFeedbackService {

    List<MenuItemFeedback> getMenuItemsFeedbackByFeedback(Feedback feedback);

    MenuItemDto giveFeedbackToCartItem(MenuItemFeedback menuItemFeedback, Integer rating, String review);
}
