package com.ruyi.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String User_Id;
    private String User_name;
    private String User_sex;
    private int User_age;
    private String User_preference;
    private String User_medicalhistory;
    private String User_allergyhistory;
    private String User_password;
    private String User_phonenumber;
    private String User_email;
    private String User_head;
    private String User_note;
}
