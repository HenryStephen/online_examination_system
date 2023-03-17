package com.ssm.online_examination_system.mapper.manual;

import com.ssm.online_examination_system.po.Score;
import com.ssm.online_examination_system.vo.ScoreAndUser;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreDao {

	/**
	 * 根据选择查询成绩
	 * @param score
	 * @return
	 */
	List<ScoreAndUser> selectScoreAndUser(Score score);

	/**
	 * 根据paperId查询成绩
	 * @param score
	 * @return
	 */
	List<Score> selectScoreBypaperId(Score score);
}
