package com.example.bankmanagement.Service;

import com.example.bankmanagement.ApiResponse.ApiException;
import com.example.bankmanagement.DTO.EmployeeDTO;
import com.example.bankmanagement.DTO.EmployeeDTOout;
import com.example.bankmanagement.Model.Employee;
import com.example.bankmanagement.Model.MyUser;
import com.example.bankmanagement.Repository.AuthRepository;
import com.example.bankmanagement.Repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final AuthRepository authRepository;

    public List<Employee> getAllEmployee(){
        return employeeRepository.findAll();
    }

    public  void registerEmployee(EmployeeDTO employeeDTO){
        MyUser myUser=authRepository.findMyUserByUsername(employeeDTO.getUserName());
        if (myUser != null) {
            throw new ApiException("User already exists");
        }

        MyUser myUser1=new MyUser();

        myUser1.setUsername(employeeDTO.getUserName());
        myUser1.setPassword(new BCryptPasswordEncoder().encode(employeeDTO.getPassword()));
        myUser1.setEmail(employeeDTO.getEmail());
        myUser1.setName(employeeDTO.getName());
        myUser1.setRole("EMPLOYEE");


        Employee employee=new Employee();
        employee.setId(null);
        employee.setPosition(employeeDTO.getPosition());
        employee.setSalary(employeeDTO.getSalary());

        employee.setMyUser(myUser1);


        authRepository.save(myUser1);
        employeeRepository.save(employee);
    }


    public List<EmployeeDTOout> getAllEmployees(Integer adminId) {
        MyUser user = authRepository.findMyUserById(adminId);
        if (user == null)
            throw new ApiException("Admin not found");

        if (user.getRole().equals("ADMIN")) {
            List<Employee> employees = employeeRepository.findAll();
            List<EmployeeDTOout> employeeDTOS = new ArrayList<>();

            for (Employee e : employees) {
                employeeDTOS.add(new EmployeeDTOout(e.getMyUser().getUsername(), e.getMyUser().getName(),
                        e.getMyUser().getEmail(),
                        e.getPosition(),
                        e.getSalary()));
            }
            return employeeDTOS;
        }
        else throw new ApiException("You don't have the permission to access this endpoint");
    }

    public void updateEmployee(Integer authId, Integer employeeId,EmployeeDTO employeeDTO) {
        MyUser auth = authRepository.findMyUserById(authId);
        if (auth == null)
            throw new ApiException("Admin was not found");

        MyUser oldEmployee = authRepository.findMyUserById(employeeId);
        if (oldEmployee == null)
            throw new ApiException("Employee was not found");

        if (authId.equals(employeeId) || auth.getRole().equals("ADMIN")) {
            oldEmployee.setUsername(employeeDTO.getUserName());
            oldEmployee.setPassword(new BCryptPasswordEncoder().encode(employeeDTO.getPassword()));
            oldEmployee.setName(employeeDTO.getName());
            oldEmployee.setEmail(employeeDTO.getEmail());
            oldEmployee.getEmployee().setPosition(employeeDTO.getPosition());
            oldEmployee.getEmployee().setSalary(employeeDTO.getSalary());
            authRepository.save(oldEmployee);
        } else throw new ApiException("You don't have  access to update this employee");
    }

    public void deleteEmployee(Integer authId, Integer employeeId) {
        MyUser auth = authRepository.findMyUserById(authId);
        if (auth == null)
            throw new ApiException("Admin was not found");

        MyUser oldEmployee = authRepository.findMyUserById(employeeId);
        if (oldEmployee == null)
            throw new ApiException("Employee with ID: " + employeeId + " was not found");

        if (authId.equals(employeeId) || auth.getRole().equals("ADMIN"))
            authRepository.delete(oldEmployee);
        else throw new ApiException("You don't have the access to delete this employee");
    }


}
