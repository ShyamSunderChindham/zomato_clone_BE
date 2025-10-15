package com.shyam_chindham.zomato_clone.repositories;

import com.shyam_chindham.zomato_clone.entities.Feedback;
import com.shyam_chindham.zomato_clone.entities.MenuItem;
import com.shyam_chindham.zomato_clone.entities.MenuItemFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemFeedbackRepository extends JpaRepository<MenuItemFeedback,Long> {
    List<MenuItemFeedback> findByFeedback(Feedback feedback);

    List<MenuItemFeedback> findByMenuItem(MenuItem menuItem);
}
