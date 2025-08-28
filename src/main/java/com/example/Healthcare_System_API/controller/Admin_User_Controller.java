package com.example.Healthcare_System_API.controller;
import com.example.Healthcare_System_API.security.Role;

import com.example.Healthcare_System_API.model.postgres.Users;
import com.example.Healthcare_System_API.service.Audit_log_service;
import com.example.Healthcare_System_API.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class Admin_User_Controller {

    @Autowired
    private UserService userService;

    @Autowired
    private Audit_log_service auditLogService;

    @PreAuthorize("hasAuthority('manage_users')")
    @PostMapping
    public ResponseEntity<Users> createUser(@RequestBody CreateUserRequest req){

        Users u = new Users();
        u.setUsername(req.username());
        u.setPassword(req.password());
        u.setRole(Role.valueOf(req.role()));  //PATIENT | DOCTOR | NURSE | ADMINISTRATOR
        Users created = userService.admin_register(u);
        // Log the admin's action
        auditLogService.logCurrentUser("CREATE_USER");
        return ResponseEntity.status(HttpStatus.CREATED).body(created);

    }

    public record CreateUserRequest(String username, String password, String role){



    }

}
