package com.shyam_chindham.zomato_clone.dtos;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String email;
    private String password;
}
