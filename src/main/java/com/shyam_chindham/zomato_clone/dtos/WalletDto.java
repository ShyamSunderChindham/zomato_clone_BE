package com.shyam_chindham.zomato_clone.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletDto {

    private Long id;

    @JsonIgnore
    private UserDto user;

    private double balance = 0.0;

    @JsonIgnore
    private List<WalletTransactionDto> transactions;
}
