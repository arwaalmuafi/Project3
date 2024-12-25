package com.example.bankmanagement.Repository;

import com.example.bankmanagement.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer ,Integer> {

    Customer findCustomerById(Integer id);
}
