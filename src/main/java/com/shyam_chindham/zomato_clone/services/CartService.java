package com.shyam_chindham.zomato_clone.services;

import com.shyam_chindham.zomato_clone.dtos.CartDto;
import com.shyam_chindham.zomato_clone.entities.Cart;
import com.shyam_chindham.zomato_clone.entities.CartItem;
import com.shyam_chindham.zomato_clone.entities.Customer;
import com.shyam_chindham.zomato_clone.entities.Restaurant;
import com.shyam_chindham.zomato_clone.entities.enums.CartStatus;

import java.util.Optional;

public interface CartService {

    void deleteCart(Long cartId);
    CartDto removeCartItemFromCart(CartItem cartItem, Cart cart);
    Cart getCartById(Long cartId);
    Cart createNewCart(Restaurant restaurant,Customer customer);
    Optional<Cart> getCartByCustomerAndRestaurantAndCartStatus(Customer customer, Restaurant restaurant, CartStatus cartStatus);
    Cart updateCartStatus(Cart cart, CartStatus cartStatus);
}
