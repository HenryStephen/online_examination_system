package com.ssm.online_examination_system.service;

import com.ssm.online_examination_system.po.Paper;
import com.ssm.online_examination_system.vo.AutoSelectQuestion;
import com.ssm.online_examination_system.vo.PaperDetailAndQuestion;

import java.util.List;

public interface PaperService {

	/**
	 * 根据试卷Id删除试卷
	 * @param paperId
	 * @return
	 */
	boolean deletePaper(Integer paperId);

	/**
	 * 添加试卷
	 * @param paper
	 * @return
	 */
	boolean addPaper(Paper paper);

	/**
	 * 修改试卷
	 * @param paper
	 * @return
	 */
	boolean updatePaper(Paper paper);

	/**
	 * 根据试卷Id查找试卷
	 * @param paperId
	 * @return
	 */
	Paper queryPaper(int paperId);

	/**
	 * 查找试卷列表
	 * @param paper
	 * @return
	 */
	List<Paper> queryPaperList(Paper paper);

	/**
	 * 根据选择题和判断题难度和选择题和判断题个数进行分题
	 * @param autoSelectQuestion
	 * @return
	 */
	List<PaperDetailAndQuestion> autoSelectQuestionByNum(AutoSelectQuestion autoSelectQuestion);

	/**
	 * 查询已经有成绩的试卷
	 * @param paper
	 * @return
	 */
	List<Paper> queryPaperListAlreadyScore(Paper paper);
}
