package com.ssm.online_examination_system.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subject {
    /**
     * 科目ID
     */
    private Integer subjectId;

    /**
     * 科目名称
     */
    private String subjectName;

}