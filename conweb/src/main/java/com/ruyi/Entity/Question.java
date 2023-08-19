package com.ruyi.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    private String Question_Id;
    private String Question_type;
    private String Question_feature;
}
