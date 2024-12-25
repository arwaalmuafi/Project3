package com.example.bankmanagement.Service;

import com.example.bankmanagement.ApiResponse.ApiException;
import com.example.bankmanagement.Model.MyUser;
import com.example.bankmanagement.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;

    public List<MyUser> getAllUsers(Integer adminId) {
        MyUser admin = authRepository.findMyUserById(adminId);
        if (admin == null){
            throw new ApiException("Admin Cannot found Admin ");
        }

        if (admin.getRole().equalsIgnoreCase("ADMIN"))
            return authRepository.findAll();

        else throw new ApiException("You don't have the permission to access this endpoint");
    }







}
