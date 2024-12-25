package com.example.bankmanagement.Service;

import com.example.bankmanagement.ApiResponse.ApiException;
import com.example.bankmanagement.Model.MyUser;
import com.example.bankmanagement.Repository.AuthRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final AuthRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser myUser=authRepository.findMyUserByUsername(username);

        if(myUser==null){
            throw new ApiException("wrong username or password");
        }
        return myUser;
    }
}
