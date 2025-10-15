package com.shyam_chindham.zomato_clone.dtos;

import com.shyam_chindham.zomato_clone.entities.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String name;
    private String email;
    private Long contactNumber;
    private Set<Role> roles;
}
