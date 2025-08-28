package com.example.Healthcare_System_API.service;


import com.example.Healthcare_System_API.model.UserPrincipal;
import com.example.Healthcare_System_API.model.postgres.Users;
import com.example.Healthcare_System_API.repository.postgres.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService{

    @Autowired
    private UsersRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user =repo.findByUsername(username);

        if(user == null){
                System.out.println("User not found");
                throw new UsernameNotFoundException("User not found");
        }

        return new UserPrincipal(user);

    }
}
