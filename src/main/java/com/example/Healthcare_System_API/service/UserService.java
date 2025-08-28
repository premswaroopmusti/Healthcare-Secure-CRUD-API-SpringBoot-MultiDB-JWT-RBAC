package com.example.Healthcare_System_API.service;

import com.example.Healthcare_System_API.model.postgres.Users;
import com.example.Healthcare_System_API.repository.postgres.UsersRepository;
import com.example.Healthcare_System_API.security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtService jwtService;

    public Users register(Users user){

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.PATIENT);
        return usersRepository.save(user);
    }

    public Users admin_register(Users user){

        user.setPassword(passwordEncoder.encode((user.getPassword())));
        return usersRepository.save(user);
    }

    public String verify(Users user){

        Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(user.getUsername());
        }
        return "Fail";

    }

}
