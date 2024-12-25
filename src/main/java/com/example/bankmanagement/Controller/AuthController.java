package com.example.bankmanagement.Controller;

import com.example.bankmanagement.Model.MyUser;
import com.example.bankmanagement.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class AuthController {
    private final AuthService authService;

    @GetMapping("/get")
    public ResponseEntity getAllUsers(@AuthenticationPrincipal MyUser myUser) {
        return ResponseEntity.status(200).body(authService.getAllUsers(myUser.getId()));
    }
}
