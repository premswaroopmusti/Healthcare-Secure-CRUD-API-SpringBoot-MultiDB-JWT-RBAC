package com.example.Healthcare_System_API.service;

import com.example.Healthcare_System_API.model.postgres.Audit_Logs;
import com.example.Healthcare_System_API.model.postgres.Users;
import com.example.Healthcare_System_API.repository.postgres.Audit_logs_repo;
import com.example.Healthcare_System_API.repository.postgres.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

@Service
public class Audit_log_service {

        @Autowired
        UsersRepository usersRepository;

        @Autowired
        Audit_logs_repo auditLogsRepo;


        public void logCurrentUser(String action, Integer patientId){

            Integer userId = ResolveCurrentUserId();
            auditLogsRepo.save(new Audit_Logs(action, patientId, userId));
        }


        public void logCurrentUser(String action){

            Integer userId = ResolveCurrentUserId();
            auditLogsRepo.save(new Audit_Logs(action, userId));

        }

        public Integer ResolveCurrentUserId(){

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if(auth == null){
                return null;
            }

            String username = auth.getName();
            if(username == null){
                return null;
            }

            Users u = usersRepository.findByUsername(username);
            return (u != null) ? u.getId() :null;


        }

}
