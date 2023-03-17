package com.ssm.online_examination_system.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreKey {
    /**
     * 试卷ID
     */
    private Integer paperId;

    /**
     * 学生ID
     */
    private Integer userId;

}