package com.shyam_chindham.zomato_clone.dtos;

import com.shyam_chindham.zomato_clone.entities.Feedback;
import com.shyam_chindham.zomato_clone.entities.MenuItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemFeedbackDto {

    private Long id;
    private Integer menuItemRating;
    private String menuItemReview;

    private MenuItem menuItem;

    private Feedback feedback;
}
