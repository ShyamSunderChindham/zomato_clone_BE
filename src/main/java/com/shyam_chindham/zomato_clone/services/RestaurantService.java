package com.shyam_chindham.zomato_clone.services;

import com.shyam_chindham.zomato_clone.entities.Restaurant;
import com.shyam_chindham.zomato_clone.entities.RestaurantPartner;
import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface RestaurantService {
    Restaurant getRestaurantById(Long restaurantId);
    Restaurant createRestaurant(Restaurant restaurant,RestaurantPartner restaurantPartner);

    Restaurant getRestaurantByRestaurantPartner(RestaurantPartner restaurantPartner);
    Restaurant updateRestaurantDetails(Restaurant restaurant,Long restaurantId);

    Restaurant updateRestaurantStatus(Boolean isOpen, Restaurant restaurant);

    Page<Restaurant> getRestaurantsNearByCustomer(Point customerLocation, PageRequest pageRequest);
}
