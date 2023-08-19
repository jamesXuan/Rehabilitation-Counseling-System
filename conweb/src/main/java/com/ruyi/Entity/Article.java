package com.ruyi.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    private String Article_id;
    private String Article_type;
    private String Article_title;
    private String Article_context;
    private String Article_time;
    private String Article_author;
    private int Article_viewnumber;
    private int Article_compliment;
    private String Article_img;
    private String Article_note;
}
