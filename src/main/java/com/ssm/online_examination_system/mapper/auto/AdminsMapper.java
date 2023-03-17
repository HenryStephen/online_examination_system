package com.ssm.online_examination_system.mapper.auto;

import com.ssm.online_examination_system.po.Admins;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminsMapper {
    /**
     *
     * @mbggenerated 2019-08-16
     */
    int deleteByPrimaryKey(Integer adminId);

    /**
     *
     * @mbggenerated 2019-08-16
     */
    int insert(Admins record);

    /**
     *
     * @mbggenerated 2019-08-16
     */
    int insertSelective(Admins record);

    /**
     *
     * @mbggenerated 2019-08-16
     */
    Admins selectByPrimaryKey(Integer adminId);

    /**
     *
     * @mbggenerated 2019-08-16
     */
    int updateByPrimaryKeySelective(Admins record);

    /**
     *
     * @mbggenerated 2019-08-16
     */
    int updateByPrimaryKey(Admins record);
}