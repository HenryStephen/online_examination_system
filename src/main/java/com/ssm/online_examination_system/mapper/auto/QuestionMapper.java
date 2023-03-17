package com.ssm.online_examination_system.mapper.auto;

import com.ssm.online_examination_system.po.Question;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionMapper {
    /**
     *
     * @mbggenerated 2019-08-16
     */
    int deleteByPrimaryKey(Integer questionId);

    /**
     *
     * @mbggenerated 2019-08-16
     */
    int insert(Question record);

    /**
     *
     * @mbggenerated 2019-08-16
     */
    int insertSelective(Question record);

    /**
     *
     * @mbggenerated 2019-08-16
     */
    Question selectByPrimaryKey(Integer questionId);

    /**
     *
     * @mbggenerated 2019-08-16
     */
    int updateByPrimaryKeySelective(Question record);

    /**
     *
     * @mbggenerated 2019-08-16
     */
    int updateByPrimaryKey(Question record);
}