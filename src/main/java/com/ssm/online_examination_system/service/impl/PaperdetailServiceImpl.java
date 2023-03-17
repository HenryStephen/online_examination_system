package com.ssm.online_examination_system.service.impl;

import com.ssm.online_examination_system.mapper.auto.PaperdetailMapper;
import com.ssm.online_examination_system.mapper.manual.PaperdetailDao;
import com.ssm.online_examination_system.po.Paperdetail;
import com.ssm.online_examination_system.service.PaperdetailService;
import com.ssm.online_examination_system.vo.PaperDetailAndQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class PaperdetailServiceImpl implements PaperdetailService {

	@Autowired
	private PaperdetailMapper paperdetailMapper;

	@Autowired
	private PaperdetailDao paperdetailDao;

	/**
	 * 添加试卷详情
	 * @param paperdetail
	 * @return
	 */
	@Override
	public boolean addPaperDeatil(Paperdetail paperdetail) {
			int count = paperdetailMapper.insertSelective(paperdetail);
			if(count == 0){
				return false;
			}
		return true;
	}

	/**
	 * 删除试卷详情
	 * @param paperId
	 * @return
	 */
	@Override
	public boolean deletePaperDetail(Integer paperId) {
		int count = paperdetailDao.deleteByPaperId(paperId);
		if(count > 0){
			return true;
		}
		return false;
	}

	/**
	 * 根据paperId查看试卷详情和题目
	 * @param paperId
	 * @return
	 */
	@Override
	public List<PaperDetailAndQuestion> selectPaperDetailAndQuestionByPaperId(Integer paperId) {
		List<PaperDetailAndQuestion> paperDetailAndQuestionList = null;
		paperDetailAndQuestionList = paperdetailDao.selectPaperDetailAndQuestionByPaperId(paperId);
		if(paperDetailAndQuestionList!=null && paperDetailAndQuestionList.size()!=0){
			Collections.sort(paperDetailAndQuestionList, new Comparator<PaperDetailAndQuestion>() {
				@Override
				public int compare(PaperDetailAndQuestion o1, PaperDetailAndQuestion o2) {
					if(o1.getQuestionNo()<o2.getQuestionNo()) {
						//return -1:即为正序排序
						return -1;
					}else if (o1.getQuestionNo() == o2.getQuestionNo()) {
						return 0;
					}else {
						//return 1: 即为倒序排序
						return 1;
					}
				}
			});
		}
		return paperDetailAndQuestionList;
	}
}
