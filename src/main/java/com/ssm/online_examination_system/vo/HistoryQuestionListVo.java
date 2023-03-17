package com.ssm.online_examination_system.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author lowry
 * @CreateDate 2019/8/25 0:24
 * @Version 1.0
 */
@Data
public class HistoryQuestionListVo {

    /**
     * 封装的历史答题情况的集合
     */
    private List<HistoryQuestionVo> listVo;

}

