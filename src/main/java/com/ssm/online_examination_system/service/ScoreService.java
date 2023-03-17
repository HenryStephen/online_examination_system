package com.ssm.online_examination_system.service;

import com.github.pagehelper.PageInfo;
import com.ssm.online_examination_system.po.Score;
import com.ssm.online_examination_system.vo.ScoreAndUser;

import java.util.List;

public interface ScoreService {

	/**
	 * 查询成绩
	 * @param score
	 * @return
	 */
	Score queryScore(Score score);

	/**
	 * 成绩信息统计
	 * @param score
	 * @return
	 */
	PageInfo<ScoreAndUser> queryScoreList(Score score, Integer PageSize, Integer PageNum);

	/**
	 *添加成绩
	 * @param score
	 * @return
	 */
	boolean insertScore(Score score);

	/**
	 * 根据试卷查看学生成绩
	 * @param score
	 * @return
	 */
	List<Score> queryScoreList(Score score);
}
