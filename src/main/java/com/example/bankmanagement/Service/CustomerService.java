package com.example.bankmanagement.Service;

import com.example.bankmanagement.ApiResponse.ApiException;
import com.example.bankmanagement.DTO.AccountDTOout;
import com.example.bankmanagement.DTO.CustomerDTO;
import com.example.bankmanagement.DTO.CustomerDTOout;
import com.example.bankmanagement.Model.Account;
import com.example.bankmanagement.Model.Customer;
import com.example.bankmanagement.Model.MyUser;
import com.example.bankmanagement.Repository.AuthRepository;
import com.example.bankmanagement.Repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AuthRepository authRepository;

    public void registerCustomer(CustomerDTO customerDTO) {
        MyUser myUser = authRepository.findMyUserByUsername(customerDTO.getUserName());
        if (myUser != null) {
            throw new ApiException("User already exists");
        }

        MyUser myUser1 = new MyUser();

        myUser1.setUsername(customerDTO.getUserName());
        myUser1.setPassword(new BCryptPasswordEncoder().encode(customerDTO.getPassword()));
        myUser1.setEmail(customerDTO.getEmail());
        myUser1.setName(customerDTO.getName());
        myUser1.setRole("CUSTOMER");

        Customer customer = new Customer();
        customer.setId(null);
        customer.setPhoneNumber(customerDTO.getPhoneNumber());

        customer.setMyUser(myUser1);

        authRepository.save(myUser1);
        customerRepository.save(customer);
    }

    public List<CustomerDTOout> getAllCustomer(Integer auth) {
        MyUser myUser = authRepository.findMyUserById(auth);
        if (myUser == null) {
            throw new ApiException("Cannot found Account");
        }
        if (!myUser.getRole().equals("EMPLOYEE") || !myUser.getRole().equals("ADMIN")) {
            throw new ApiException("Sorry , You do not have the authority to see this Details ");
        }
        List<Customer> customerList = customerRepository.findAll();
        if (customerList.isEmpty()) {
            throw new ApiException("There no Customer yet");
        }
        List<CustomerDTOout> customerDTOS = new ArrayList<>();
        for (Customer cc : customerList) {
            List<AccountDTOout> accountDTOS = new ArrayList<>();
            for (Account aa : cc.getAccounts())
                accountDTOS.add(new AccountDTOout(aa.getAccountNumber(), aa.getBalance(), aa.getIsActive()));

            customerDTOS.add(new CustomerDTOout(cc.getMyUser().getUsername(), cc.getMyUser().getEmail(), cc.getMyUser().getName(), cc.getPhoneNumber(), accountDTOS));
        }
        return customerDTOS;
    }
    public void updateCustomer(Integer authId, Integer customerId,CustomerDTO customerDTO) {
        MyUser auth = authRepository.findMyUserById(authId);
        if (auth == null){
            throw new ApiException("Admin was not found");
        }

        MyUser oldCustomer = authRepository.findMyUserById(customerId);
        if (oldCustomer == null)
            throw new ApiException("Customer not found");

        if (!authId.equals(customerId)||!auth.getRole().equals("ADMIN") || !auth.getRole().equals("EMPLOYEE")) {
            throw new ApiException("You don't have access to update this customer");
        }
        oldCustomer.setUsername(customerDTO.getUserName());
        oldCustomer.setPassword(new BCryptPasswordEncoder().encode(customerDTO.getPassword()));
        oldCustomer.setName(customerDTO.getName());
        oldCustomer.setEmail(customerDTO.getEmail());
        oldCustomer.getCustomer().setPhoneNumber(customerDTO.getPhoneNumber());
        authRepository.save(oldCustomer);
    }


    public void deleteCustomer(Integer auth, Integer customerId) {
        MyUser user = authRepository.findMyUserById(auth);
        if (user == null) {
            throw new ApiException("Cannot found Account ");
        }

        MyUser customer = authRepository.findMyUserById(customerId);
        if (customer == null) {
            throw new ApiException("Cannot found customer");
        }

        if (auth.equals(customerId) || user.getRole().equals("ADMIN"))
            authRepository.delete(customer);
        else throw new ApiException("You Cannot delete this customer");
    }
}
