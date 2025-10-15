package com.shyam_chindham.zomato_clone.services;

import com.shyam_chindham.zomato_clone.dtos.CartItemDto;
import com.shyam_chindham.zomato_clone.entities.Cart;
import com.shyam_chindham.zomato_clone.entities.CartItem;
import com.shyam_chindham.zomato_clone.entities.MenuItem;

public interface CartItemService {
    CartItem getCartItemById(Long cartItemId);
    CartItem createNewCartItem(MenuItem menuItem, Cart cart);
    CartItemDto incrementCartItemQuantity(Integer quantity, CartItem cartItem);
    CartItemDto decrementCartItemQuantity(Integer quantity, CartItem cartItem);
    void removeCartItemFromCart(CartItem cartItem);
}
