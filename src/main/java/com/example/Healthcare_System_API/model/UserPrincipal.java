package com.example.Healthcare_System_API.model;

import com.example.Healthcare_System_API.model.postgres.Users;
import com.example.Healthcare_System_API.security.Permission;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {

            private final Users user;

            public UserPrincipal(Users user){
                    this.user = user;
            }

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities(){

                Set<Permission>perms =user.getRole().getPermissions();
                return perms.stream()
                        .map(p -> new SimpleGrantedAuthority(p.getValue()))
                        .collect(Collectors.toSet());
            }

            @Override
            public String getPassword(){
                return user.getPassword();
             }

             @Override
            public String getUsername(){
                return user.getUsername();
             }


}
