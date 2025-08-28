package com.example.Healthcare_System_API.controller;

import com.example.Healthcare_System_API.model.postgres.Users;
import com.example.Healthcare_System_API.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
public class User_Controller {

        @Autowired
        private UserService userService;

        @PostMapping("/register")
        public Users register(@RequestBody Users users) {
            return userService.register(users);
        }

        @PostMapping("/auth/login")
        public ResponseEntity<?>authLogin(@RequestBody Users users){

                String token = userService.verify(users);
                if("Fail".equals(token)){
                    return ResponseEntity.status(401).body(Map.of("error" , "Invalid Credentials"));
                }

                return ResponseEntity.ok(Map.of("token", token, "expires in", 1800));
        }

}
