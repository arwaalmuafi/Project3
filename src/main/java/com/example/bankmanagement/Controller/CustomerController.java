package com.example.bankmanagement.Controller;

import com.example.bankmanagement.ApiResponse.ApiResponse;
import com.example.bankmanagement.DTO.CustomerDTO;
import com.example.bankmanagement.Model.MyUser;
import com.example.bankmanagement.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/api/customer")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/registerCustomer")
    public ResponseEntity registerCustomer(@RequestBody @Valid CustomerDTO customerDTO){
        customerService.registerCustomer(customerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("customer added"));

    }

    @GetMapping("/get")
    public ResponseEntity getAllCustomers(@AuthenticationPrincipal MyUser myUser) {
        return ResponseEntity.status(200).body(customerService.getAllCustomer(myUser.getId()));
    }

    @PutMapping("/update/{customerId}")
    public ResponseEntity updateCustomer(@AuthenticationPrincipal MyUser myUser,
                                         @PathVariable Integer customerId,
                                         @RequestBody @Valid CustomerDTO customerDTO) {
        customerService.updateCustomer(myUser.getId(), customerId, customerDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Customer has been updated successfully"));
    }

    @DeleteMapping("/delete/{customerId}")
    public ResponseEntity deleteCustomer(@AuthenticationPrincipal MyUser myUser,
                                         @PathVariable Integer customerId) {
        customerService.deleteCustomer(myUser.getId(), customerId);
        return ResponseEntity.status(200).body(new ApiResponse("Customer has been deleted successfully"));
    }
}



