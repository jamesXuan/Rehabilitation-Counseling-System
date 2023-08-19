package com.ruyi.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Consul {
    private String Record_Id;
    private String User_Id;
    private Date Record_Time;
    private String Record_topic;
    private String Record_context;
    private String Record_title;
    private String Record_img;
    private String Record_note;
}
