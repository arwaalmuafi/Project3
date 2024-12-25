package com.example.bankmanagement.Repository;

import com.example.bankmanagement.Model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<MyUser ,Integer> {

    MyUser findMyUserById(Integer id);

    MyUser findMyUserByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
