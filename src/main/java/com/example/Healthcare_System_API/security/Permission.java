package com.example.Healthcare_System_API.security;

public enum Permission {

        VIEW_MY_RECORDS("view_my_records"),
        VIEW_ALL_RECORDS("view_all_records"),
        VIEW_RECORDS_BY_PATIENT_ID("view_records_by_patient_id"),
        CREATE_RECORD("create_record"),
        UPDATE_STATUS("update_status"),
        PRESCRIBE_MEDICATION("prescribe_medication"),
        MANAGE_USERS("manage_users");


        private String value;

        private Permission(){

        }

        Permission(String value){
            this.value = value;
        }

        public String getValue() {          // getter method
                return value;
        }

}
