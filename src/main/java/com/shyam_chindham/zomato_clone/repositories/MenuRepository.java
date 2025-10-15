package com.shyam_chindham.zomato_clone.repositories;

import com.shyam_chindham.zomato_clone.entities.Menu;
import com.shyam_chindham.zomato_clone.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu,Long> {

    List<Menu> findByRestaurant(Restaurant restaurant);
}
