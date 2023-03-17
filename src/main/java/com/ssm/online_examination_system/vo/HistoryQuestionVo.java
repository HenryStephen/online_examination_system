package com.ssm.online_examination_system.vo;

import lombok.Data;

/**
 * @Author lowry
 * @CreateDate 2019/8/25 0:04
 * @Version 1.0
 */
@Data
public class HistoryQuestionVo {
    /**
     * 所写答案
     */
    private String userQuestion;

    /**
     * 是否正确【1为错误，2为正确】
     */
    private Integer isRight;
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
     * 所有的分值
     */
    private Integer grade;

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
    /**
     * 科目名称
     */
    private String subjectName;

    /**
     * 题目序号
     */
    private Integer questionNo;

}
