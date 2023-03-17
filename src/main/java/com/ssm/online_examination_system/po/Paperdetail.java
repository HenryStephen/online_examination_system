package com.ssm.online_examination_system.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paperdetail extends PaperdetailKey {
    /**
     * 题目序号
     */
    private Integer questionNo;
}