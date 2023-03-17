package com.ssm.online_examination_system.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Score extends ScoreKey {
    /**
     * 成绩
     */
    private Integer score;

}