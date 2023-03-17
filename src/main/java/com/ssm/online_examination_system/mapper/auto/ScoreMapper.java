package com.ssm.online_examination_system.mapper.auto;

import com.ssm.online_examination_system.po.Score;
import com.ssm.online_examination_system.po.ScoreKey;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreMapper {
    /**
     *
     * @mbggenerated 2019-08-16
     */
    int deleteByPrimaryKey(ScoreKey key);

    /**
     *
     * @mbggenerated 2019-08-16
     */
    int insert(Score record);

    /**
     *
     * @mbggenerated 2019-08-16
     */
    int insertSelective(Score record);

    /**
     *
     * @mbggenerated 2019-08-16
     */
    Score selectByPrimaryKey(ScoreKey key);

    /**
     *
     * @mbggenerated 2019-08-16
     */
    int updateByPrimaryKeySelective(Score record);

    /**
     *
     * @mbggenerated 2019-08-16
     */
    int updateByPrimaryKey(Score record);
}