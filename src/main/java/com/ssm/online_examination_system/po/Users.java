package com.ssm.online_examination_system.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    /**
     *用户ID
     */
    private Integer userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 手机号
     */
    private String userTelephone;

    /**
     * 邮箱
     */
    private String userEmail;

    /**
     * 性别【1代表女，2代表男】
     */
    private Integer userSex;

    /**
     * 真实姓名
     */
    private String userRealname;

    /**
     * 状态【1代表禁用，2代表启用】
     */
    private Integer userStatus;

    /**
     * 院校
     */
    private String userAcademy;

    /**
     * 用户角色【1为老师，2为学生】
     */
    private Integer userRole;


}