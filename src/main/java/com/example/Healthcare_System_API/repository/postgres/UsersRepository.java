package com.example.Healthcare_System_API.repository.postgres;

import com.example.Healthcare_System_API.model.postgres.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Integer> {

        Users findByUsername(String username);



}
