package com.ssm.online_examination_system.mapper.auto;

import com.ssm.online_examination_system.po.Paper;
import org.springframework.stereotype.Repository;

@Repository
public interface PaperMapper {
    /**
     *
     * @mbggenerated 2019-08-16
     */
    int deleteByPrimaryKey(Integer paperId);

    /**
     *
     * @mbggenerated 2019-08-16
     */
    int insert(Paper record);

    /**
     *
     * @mbggenerated 2019-08-16
     */
    int insertSelective(Paper record);

    /**
     *
     * @mbggenerated 2019-08-16
     */
    Paper selectByPrimaryKey(Integer paperId);

    /**
     *
     * @mbggenerated 2019-08-16
     */
    int updateByPrimaryKeySelective(Paper record);

    /**
     *
     * @mbggenerated 2019-08-16
     */
    int updateByPrimaryKey(Paper record);
}