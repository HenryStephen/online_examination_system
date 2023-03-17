package com.ssm.online_examination_system.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    /**
     * 题目ID
     */
    private Integer questionId;

    /**
     * 题目
     */
    private String question;

    /**
     * 正确答案【0、1、A、B、C、D】
     */
    private String rightAnswer;

    /**
     * 选项A
     */
    private String optionA;

    /**
     * 选项B
     */
    private String optionB;

    /**
     * 选项C
     */
    private String optionC;

    /**
     * 选项D
     */
    private String optionD;

    /**
     * 解析
     */
    private String analysis;

    /**
     * 分值
     */
    private Integer grade;

    /**
     * 难易程度【1为简单，2为中等，3为困难】
     */
    private Integer complexity;

    /**
     * 关键词
     */
    private String questionKey;

    /**
     * 题目类型【1为选择题，2为判断题】
     */
    private Integer questionType;

    /**
     * 科目ID
     */
    private Integer subjectId;

}