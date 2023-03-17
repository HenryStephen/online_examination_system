package com.ssm.online_examination_system.mapper.auto;

import com.ssm.online_examination_system.po.Historyquestion;
import com.ssm.online_examination_system.po.HistoryquestionKey;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryquestionMapper {
    /**
     *
     * @mbggenerated 2019-08-16
     */
    int deleteByPrimaryKey(HistoryquestionKey key);

    /**
     *
     * @mbggenerated 2019-08-16
     */
    int insert(Historyquestion record);

    /**
     *
     * @mbggenerated 2019-08-16
     */
    int insertSelective(Historyquestion record);

    /**
     *
     * @mbggenerated 2019-08-16
     */
    Historyquestion selectByPrimaryKey(HistoryquestionKey key);

    /**
     *
     * @mbggenerated 2019-08-16
     */
    int updateByPrimaryKeySelective(Historyquestion record);

    /**
     *
     * @mbggenerated 2019-08-16
     */
    int updateByPrimaryKey(Historyquestion record);
}