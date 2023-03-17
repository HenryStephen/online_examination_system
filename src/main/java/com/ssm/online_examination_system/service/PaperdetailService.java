package com.ssm.online_examination_system.service;

import com.ssm.online_examination_system.po.Paperdetail;
import com.ssm.online_examination_system.vo.PaperDetailAndQuestion;

import java.util.List;

public interface PaperdetailService {

	/**
	 * 添加试卷详情
	 * @param paperdetail
	 * @return
	 */
	boolean addPaperDeatil(Paperdetail paperdetail);

	/**
	 * 删除试卷详情
	 * @param paperId
	 * @return
	 */
	boolean deletePaperDetail(Integer paperId);

	/**
	 * 根据paperId查看试卷详情和题目
	 * @param paperId
	 * @return
	 */
	List<PaperDetailAndQuestion> selectPaperDetailAndQuestionByPaperId(Integer paperId);
}
