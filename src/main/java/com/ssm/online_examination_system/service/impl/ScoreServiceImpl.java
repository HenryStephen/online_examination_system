package com.ssm.online_examination_system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssm.online_examination_system.mapper.auto.ScoreMapper;
import com.ssm.online_examination_system.mapper.manual.ScoreDao;
import com.ssm.online_examination_system.po.Score;
import com.ssm.online_examination_system.service.ScoreService;
import com.ssm.online_examination_system.vo.ScoreAndUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreServiceImpl implements ScoreService {

	@Autowired
	private ScoreMapper scoreMapper;

	@Autowired
	private ScoreDao scoreDao;

	/**
	 * 查询成绩
	 * @param score
	 * @return
	 */
	@Override
	public Score queryScore(Score score) {
		return scoreMapper.selectByPrimaryKey(score);
	}

	/**
	 * 成绩信息统计
	 * @param score
	 * @return
	 */
	@Override
	public PageInfo<ScoreAndUser> queryScoreList(Score score, Integer PageSize, Integer PageNum) {
		PageHelper.startPage(PageSize,PageNum);
		List<ScoreAndUser> scoreList = scoreDao.selectScoreAndUser(score);
		if(scoreList.size()<=0)
			return null;
		return new PageInfo<ScoreAndUser>(scoreList);
	}

	/**
	 * 添加成绩
	 *
	 * @param score
	 * @return
	 */
	@Override
	public boolean insertScore(Score score) {
		int count = scoreMapper.insert(score);
		if(count > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 根据试卷查看学生成绩
	 * @param score
	 * @return
	 */
	@Override
	public List<Score> queryScoreList(Score score) {
		List<Score> scoreList = scoreDao.selectScoreBypaperId(score);
		if(scoreList == null || scoreList.size()<=0)
			return null;
		return scoreList;
	}
}
