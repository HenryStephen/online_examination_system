package com.ssm.online_examination_system.mapper.auto;

import com.ssm.online_examination_system.po.Subject;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectMapper {
    /**
     *
     * @mbggenerated 2019-08-16
     */
    int deleteByPrimaryKey(Integer subjectId);

    /**
     *
     * @mbggenerated 2019-08-16
     */
    int insert(Subject record);

    /**
     *
     * @mbggenerated 2019-08-16
     */
    int insertSelective(Subject record);

    /**
     *
     * @mbggenerated 2019-08-16
     */
    Subject selectByPrimaryKey(Integer subjectId);

    /**
     *
     * @mbggenerated 2019-08-16
     */
    int updateByPrimaryKeySelective(Subject record);

    /**
     *
     * @mbggenerated 2019-08-16
     */
    int updateByPrimaryKey(Subject record);
}