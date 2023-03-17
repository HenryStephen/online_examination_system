package com.ssm.online_examination_system.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhanghonglin
 * @Title: AuthToken
 * @ProjectName token-authentication
 * @Description: 管理员的token认证
 * @date 2019/8/20
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthToken {

}