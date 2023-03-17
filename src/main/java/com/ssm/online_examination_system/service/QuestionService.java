package com.ssm.online_examination_system.service;

import com.github.pagehelper.PageInfo;
import com.ssm.online_examination_system.po.Question;
import com.ssm.online_examination_system.vo.QuestionVo;

public interface QuestionService {

	/**
	 * 查找题目信息
	 * @param questionVo
	 * @param PageSize
	 * @param PageNum
	 * @return
	 */
	PageInfo<QuestionVo> queryQuestion(QuestionVo questionVo, Integer PageSize, Integer PageNum);

	/**
	 * 修改题目信息
	 * @param question
	 * @return
	 */
	boolean modifyQuestionInfo(Question question);

	/**
	 * 删除题目
	 * @param questionId
	 * @return
	 */
	boolean deleteQuestion(Integer questionId);

	/**
	 * 添加题目
	 * @param question
	 * @return
	 */
	boolean addQuestion(Question question);
}
