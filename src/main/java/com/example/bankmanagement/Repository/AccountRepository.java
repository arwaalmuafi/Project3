package com.example.bankmanagement.Repository;

import com.example.bankmanagement.Model.Account;
import com.example.bankmanagement.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account ,Integer> {
    List<Account> findAccountsByCustomer(Customer customer);

    Account findAccountsByIdAndCustomer(Integer accountId, Customer customer);

    Account findAccountById(Integer accountId);
}
