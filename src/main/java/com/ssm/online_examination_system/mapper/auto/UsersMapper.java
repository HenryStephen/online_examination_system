package com.ssm.online_examination_system.mapper.auto;

import com.ssm.online_examination_system.po.Users;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersMapper {

    /**
     *
     * @mbggenerated 2019-08-16
     */
    int deleteByPrimaryKey(Integer userId);

    /**
     *
     * @mbggenerated 2019-08-16
     */
    Users selectByPrimaryKey(Integer userId);

    /**
     *
     * @mbggenerated 2019-08-16
     */
    int updateByPrimaryKeySelective(Users record);

    /**
     *
     * @mbggenerated 2019-08-16
     */
    int updateByPrimaryKey(Users record);


    /**
     *
     * @mbggenerated 2019-08-16
     */
    int insert(Users record);

    /**
     *
     * @mbggenerated 2019-08-16
     */
    int insertSelective(Users record);

}