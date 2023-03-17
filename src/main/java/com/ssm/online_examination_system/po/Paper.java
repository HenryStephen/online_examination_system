package com.ssm.online_examination_system.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paper {
    /**
     * 试卷ID
     */
    private Integer paperId;

    /**
     * 试卷名称
     */
    private String paperName;

    /**
     * 试卷题目个数
     */
    private Integer questionTotalnum;

    /**
     * 试卷总分值
     */
    private Integer questionTotalgrade;

    /**
     * 试卷开始考试时间
     */
    private Date startTime;

    /**
     * 试卷结束时间
     */
    private Date endTime;

    /**
     * 试卷等级【1为公开，2为私密，3为需要密码】
     */
    private Integer paperLevel;

    /**
     * 试卷密码
     */
    private String paperPassword;

    /**
     * 出题人ID
     */
    private Integer paperSetterId;

    /**
     * 科目ID
     */
    private Integer subjectId;

    /**
     * 考试时长
     */
    private Integer examDuration;

    /**
     * 及格分数
     */
    private Integer passGrade;
}