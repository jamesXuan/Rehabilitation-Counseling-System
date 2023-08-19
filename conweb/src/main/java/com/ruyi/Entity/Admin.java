package com.ruyi.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    private String Admin_Id;
    private String Admin_name;
    private String Admin_password;
    private String Admin_limit;
}
