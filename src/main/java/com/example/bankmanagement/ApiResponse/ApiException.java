package com.example.bankmanagement.ApiResponse;

public class ApiException extends RuntimeException {

    public ApiException(String massage){
        super(massage);
    }
}
