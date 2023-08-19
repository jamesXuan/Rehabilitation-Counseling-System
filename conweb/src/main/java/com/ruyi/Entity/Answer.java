package com.ruyi.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer {
    private String Answer_Id;
    private String Answer_type;
    private String Answer_example;
    private String Answer_style;
}
