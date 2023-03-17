package com.ssm.online_examination_system.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admins {
    /**
     * 管理员ID
     */
    private Integer adminId;

    /**
     * 管理员名称
     */
    private String adminName;

    /**
     * 密码
     */
    private String adminPassword;

    /**
     * 手机号
     */
    private String adminTelephone;

    /**
     * 邮箱
     */
    private String adminEmail;

    /**
     * 性别【1代表女，2代表男】
     */
    private Integer adminSex;

    /**
     * 真实姓名
     */
    private String adminRealname;

    /**
     * 状态【1代表禁用，2代表启用】
     */
    private Integer adminStatus;

}