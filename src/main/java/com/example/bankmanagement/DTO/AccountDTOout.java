package com.example.bankmanagement.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AccountDTOout {
    private String accountNumber;
    private Double balance;
    private Boolean isActive;
}