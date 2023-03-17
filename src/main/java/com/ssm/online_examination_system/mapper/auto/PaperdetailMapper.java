package com.ssm.online_examination_system.mapper.auto;

import com.ssm.online_examination_system.po.Paperdetail;
import com.ssm.online_examination_system.po.PaperdetailKey;
import org.springframework.stereotype.Repository;

@Repository
public interface PaperdetailMapper {
    /**
     *
     * @mbggenerated 2019-08-16
     */
    int deleteByPrimaryKey(PaperdetailKey key);

    /**
     *
     * @mbggenerated 2019-08-16
     */
    int insert(Paperdetail record);

    /**
     *
     * @mbggenerated 2019-08-16
     */
    int insertSelective(Paperdetail record);

    /**
     *
     * @mbggenerated 2019-08-16
     */
    Paperdetail selectByPrimaryKey(PaperdetailKey key);

    /**
     *
     * @mbggenerated 2019-08-16
     */
    int updateByPrimaryKeySelective(Paperdetail record);

    /**
     *
     * @mbggenerated 2019-08-16
     */
    int updateByPrimaryKey(Paperdetail record);
}