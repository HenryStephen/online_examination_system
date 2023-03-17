package com.ssm.online_examination_system.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author lowry
 * @CreateDate 2019/8/24 16:01
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentInPaperVo {
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
     * 出题人姓名
     */
    private String userName;
    /**
     * 科目名称
     */
    private String subjectName;
    /**
     * 院校
     */
    private String userAcademy;

    /**
     * 考试时长
     */
    private Integer examDuration;

    /**
     * 及格分数
     */
    private Integer passGrade;
}
