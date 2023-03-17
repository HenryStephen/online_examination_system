package com.ssm.online_examination_system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssm.online_examination_system.mapper.auto.QuestionMapper;
import com.ssm.online_examination_system.mapper.manual.QuestionDao;
import com.ssm.online_examination_system.po.Question;
import com.ssm.online_examination_system.service.QuestionService;
import com.ssm.online_examination_system.vo.QuestionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
	@Autowired
	private QuestionDao questionDao;
	@Autowired
	private QuestionMapper questionMapper;

	/**
	 * 查询题目信息
	 * @param questionVo
	 * @param PageSize
	 * @param PageNum
	 * @return
	 */
	@Override
	public PageInfo<QuestionVo> queryQuestion(QuestionVo questionVo, Integer PageSize, Integer PageNum) {
		PageHelper.startPage(PageSize,PageNum);
		List<QuestionVo> questionList = questionDao.selectBySelective(questionVo);
		if(questionList.size()<=0)
			return null;
		return new PageInfo<QuestionVo>(questionList);
	}

	/**
	 * 修改题目信息
	 * @param question
	 * @return
	 */
	@Override
	public boolean modifyQuestionInfo(Question question) {
		int count = questionMapper.updateByPrimaryKeySelective(question);
		if(count>0){
			return true;
		}
		return false;
	}

	/**
	 * 删除题目
	 * @param questionId
	 * @return
	 */
	@Override
	public boolean deleteQuestion(Integer questionId) {
		int count = questionMapper.deleteByPrimaryKey(questionId);
		if(count>0){
			return true;
		}
		return false;
	}

	/**
	 * 添加题目
	 * @param question
	 * @return
	 */
	@Override
	public boolean addQuestion(Question question) {
		System.out.println(question);
		int count = questionMapper.insertSelective(question);
		if(count>0){
			return true;
		}
		return false;
	}


}
