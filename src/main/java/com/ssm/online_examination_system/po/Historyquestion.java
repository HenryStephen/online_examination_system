package com.ssm.online_examination_system.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Historyquestion extends HistoryquestionKey {
    /**
     * 所写答案
     */
    private String userQuestion;

    /**
     * 是否正确【1为错误，2为正确】
     */
    private Integer isRight;

}