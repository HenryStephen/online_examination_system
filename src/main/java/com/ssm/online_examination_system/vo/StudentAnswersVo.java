package com.ssm.online_examination_system.vo;

import lombok.Data;

/**
 * @Author lowry
 * @CreateDate 2019/8/24 15:35
 * @Version 1.0
 */
@Data
public class StudentAnswersVo {
    /**
     * 学生ID
     */
    private Integer userId;

    /**
     * 试卷ID
     */
    private Integer paperId;

    /**
     * 题目ID
     */
    private Integer questionId;
    /**
     * 所写答案
     */
    private String userQuestion;

    /**
     * 是否正确【1为错误，2为正确】
     */
    private Integer isRight;
}
