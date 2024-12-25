package com.example.bankmanagement.Controller;

import com.example.bankmanagement.ApiResponse.ApiResponse;
import com.example.bankmanagement.DTO.EmployeeDTO;
import com.example.bankmanagement.Model.MyUser;
import com.example.bankmanagement.Service.EmployeeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/api/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/registerEmployee")
    public ResponseEntity registerEmployee(@RequestBody @Valid EmployeeDTO employeeDTO) {
        employeeService.registerEmployee(employeeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Employee registered successfully"));
    }

    @GetMapping("/get")
    public ResponseEntity getAllEmployee(@AuthenticationPrincipal MyUser myUser) {
        return ResponseEntity.status(200).body(employeeService.getAllEmployees(myUser.getId()));
    }

    @PutMapping("/update/{employeeId}")
    public ResponseEntity updateEmployee(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer employeeId, @RequestBody @Valid EmployeeDTO employeeDTO) {
        employeeService.updateEmployee(myUser.getId(), employeeId, employeeDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Employee with ID: " + employeeId + " has been updated successfully"));
    }

    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity deleteEmployee(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer employeeId) {
        employeeService.deleteEmployee(myUser.getId(), employeeId);
        return ResponseEntity.status(200).body(new ApiResponse("Employee with ID: " + employeeId + " has been deleted successfully"));
    }
}
