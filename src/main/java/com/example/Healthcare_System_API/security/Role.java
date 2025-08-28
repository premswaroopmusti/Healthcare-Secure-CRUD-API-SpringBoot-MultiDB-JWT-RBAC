package com.example.Healthcare_System_API.security;

import java.util.Set;

import static com.example.Healthcare_System_API.security.Permission.*;

public enum Role {

    PATIENT(Set.of(VIEW_MY_RECORDS)),
    NURSE(Set.of(VIEW_ALL_RECORDS, VIEW_RECORDS_BY_PATIENT_ID ,UPDATE_STATUS )),
    DOCTOR(Set.of(VIEW_ALL_RECORDS, VIEW_RECORDS_BY_PATIENT_ID,UPDATE_STATUS, PRESCRIBE_MEDICATION)),
    ADMINISTRATOR(Set.of(MANAGE_USERS, CREATE_RECORD));


    private final Set<Permission> permissions;

    private Role(Set<Permission> permissions){

            this.permissions = permissions;
    }

    public Set<Permission> getPermissions(){
        return permissions;
    }




}
