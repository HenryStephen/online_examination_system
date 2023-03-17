package com.ssm.online_examination_system.service.impl;

import com.ssm.online_examination_system.mapper.auto.PaperMapper;
import com.ssm.online_examination_system.mapper.manual.PaperDao;
import com.ssm.online_examination_system.mapper.manual.QuestionDao;
import com.ssm.online_examination_system.po.Paper;
import com.ssm.online_examination_system.po.Paperdetail;
import com.ssm.online_examination_system.service.PaperService;
import com.ssm.online_examination_system.vo.AutoSelectQuestion;
import com.ssm.online_examination_system.vo.PaperDetailAndQuestion;
import com.ssm.online_examination_system.vo.QuestionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class PaperServiceImpl implements PaperService {

	@Autowired
	private PaperMapper paperMapper;

	@Autowired
	private PaperDao paperDao;

	@Autowired
	private QuestionDao questionDao;

	/**
	 * 根据试卷Id删除试卷
	 * @param paperId
	 * @return
	 */
	@Override
	public boolean deletePaper(Integer paperId) {
		int count = paperMapper.deleteByPrimaryKey(paperId);
		if(count>0){
			return true;
		}
		return false;
	}

	/**
	 * 添加试卷
	 * @param paper
	 * @return
	 */
	@Override
	public boolean addPaper(Paper paper) {
		int count = paperMapper.insertSelective(paper);
		if(count > 0)
			return true;
		return false;
	}

	/**
	 * 修改试卷
	 * @param paper
	 * @return
	 */
	@Override
	public boolean updatePaper(Paper paper) {
		int count = paperMapper.updateByPrimaryKeySelective(paper);
		if(count>0){
			return true;
		}
		return false;
	}

	/**
	 * 根据试卷Id查找试卷
	 * @param paperId
	 * @return
	 */
	@Override
	public Paper queryPaper(int paperId) {
		Paper paper = paperMapper.selectByPrimaryKey(paperId);
		return paper;
	}

	/**
	 * 查找试卷列表
	 * @param paper
	 * @return
	 */
	@Override
	public List<Paper> queryPaperList(Paper paper) {
		List<Paper> paperList = paperDao.selectBySelective(paper);
		return paperList;
	}

	/**
	 * 根据选择题和判断题难度和选择题和判断题个数进行分题
	 * @param autoSelectQuestion
	 * @return
	 */
	@Override
	public List<PaperDetailAndQuestion> autoSelectQuestionByNum(AutoSelectQuestion autoSelectQuestion) {
		List<PaperDetailAndQuestion> autoSelectQuestionList = new ArrayList<>();
		//当前题目序号
		Integer currentQuestionNo = 1;
		//获取科目Id
		Integer subjectId = autoSelectQuestion.getSubjectId();
		QuestionVo questionVo = new QuestionVo();
		questionVo.setSubjectId(subjectId);
		//根据科目Id查看该科目下的所有题目
		List<QuestionVo> questionList = questionDao.selectBySelective(questionVo);
		List<QuestionVo> judgemetList = new ArrayList<>();
		List<QuestionVo> selectionList = new ArrayList<>();
		//将选择题和判断题分开
		for(QuestionVo item : questionList){
			//代表选择题
			if(item.getQuestionType() == 1){
				selectionList.add(item);
			}else{
				judgemetList.add(item);
			}
		}
		//将选择题按照难易程度分开
		List<QuestionVo> judgement1List = new ArrayList<>();
		List<QuestionVo> judgement2List = new ArrayList<>();
		List<QuestionVo> judgement3List = new ArrayList<>();
		for(QuestionVo item : judgemetList){
			if(item.getComplexity() == 1){
				judgement1List.add(item);
			}else if(item.getComplexity() == 2){
				judgement2List.add(item);
			}else{
				judgement3List.add(item);
			}
		}
		int judgement1Length = judgement1List.size();
		int judgement2Length = judgement2List.size();
		int judgement3Length = judgement3List.size();
		//将选择题按照难易程度分开
		List<QuestionVo> selecion1List = new ArrayList<>();
		List<QuestionVo> selecion2List = new ArrayList<>();
		List<QuestionVo> selecion3List = new ArrayList<>();
		for(QuestionVo item : selectionList){
			if(item.getComplexity() == 1){
				selecion1List.add(item);
			}else if(item.getComplexity() == 2){
				selecion2List.add(item);
			}else{
				selecion3List.add(item);
			}
		}
		Integer selection1Length = selecion1List.size();
		Integer selection2Length = selecion2List.size();
		Integer selection3Length = selecion3List.size();
		//计算出选择和判断需要的个数
		Integer selectionNum = autoSelectQuestion.getSelectionNum();
		Integer judgementNum = autoSelectQuestion.getJudgementNum();
		//开始选择判断题
		Integer judgementNumCopy = judgementNum;
		int judgementComplexity = autoSelectQuestion.getJudgementComplexity();
		int flag1 = judgementComplexity;
		//只选择一个题目时
		if(judgementNumCopy == 1){
			//随机选择一个题目放进数组
			if(flag1 == 1){
				int index = 0;
				while(true){
					index = new Random().nextInt(judgement1Length);
					int questionId = judgement1List.get(index).getQuestionId();
					int i = 0;
					for(i = 0;i<autoSelectQuestionList.size();i++){
						Paperdetail paperdetail = autoSelectQuestionList.get(i);
						if(paperdetail.getQuestionId() == questionId){
							break;
						}
					}
					if(i == autoSelectQuestionList.size()){
						break;
					}
				}
				QuestionVo selectedQuestion = judgement1List.get(index);
				PaperDetailAndQuestion paperDetailAndQuestion = new PaperDetailAndQuestion();
				//设置questionId
				paperDetailAndQuestion.setQuestionId(selectedQuestion.getQuestionId());
				//设置questionNo
				paperDetailAndQuestion.setQuestionNo(currentQuestionNo);
				//设置题目
				paperDetailAndQuestion.setQuestion(selectedQuestion.getQuestion());
				//设置正确答案
				paperDetailAndQuestion.setRightAnswer(selectedQuestion.getRightAnswer());
				//设置A选项
				paperDetailAndQuestion.setOptionA(selectedQuestion.getOptionA());
				//设置B选项
				paperDetailAndQuestion.setOptionB(selectedQuestion.getOptionB());
				//设置C选项
				paperDetailAndQuestion.setOptionC(selectedQuestion.getOptionC());
				//设置D选项
				paperDetailAndQuestion.setOptionD(selectedQuestion.getOptionD());
				//设置解析
				paperDetailAndQuestion.setAnalysis(selectedQuestion.getAnalysis());
				//设置分值
				paperDetailAndQuestion.setGrade(selectedQuestion.getGrade());
				//设置题目类型
				paperDetailAndQuestion.setQuestionType(selectedQuestion.getQuestionType());
				autoSelectQuestionList.add(paperDetailAndQuestion);
			}else if(flag1 == 2){
				int index = 0;
				while(true){
					index = new Random().nextInt(judgement2Length);
					int questionId = judgement2List.get(index).getQuestionId();
					int i = 0;
					for(i = 0;i<autoSelectQuestionList.size();i++){
						Paperdetail paperdetail = autoSelectQuestionList.get(i);
						if(paperdetail.getQuestionId() == questionId){
							break;
						}
					}
					if(i == autoSelectQuestionList.size()){
						break;
					}
				}
				QuestionVo selectedQuestion = judgement2List.get(index);
				PaperDetailAndQuestion paperDetailAndQuestion = new PaperDetailAndQuestion();
				//设置questionId
				paperDetailAndQuestion.setQuestionId(selectedQuestion.getQuestionId());
				//设置questionNo
				paperDetailAndQuestion.setQuestionNo(currentQuestionNo);
				//设置题目
				paperDetailAndQuestion.setQuestion(selectedQuestion.getQuestion());
				//设置正确答案
				paperDetailAndQuestion.setRightAnswer(selectedQuestion.getRightAnswer());
				//设置A选项
				paperDetailAndQuestion.setOptionA(selectedQuestion.getOptionA());
				//设置B选项
				paperDetailAndQuestion.setOptionB(selectedQuestion.getOptionB());
				//设置C选项
				paperDetailAndQuestion.setOptionC(selectedQuestion.getOptionC());
				//设置D选项
				paperDetailAndQuestion.setOptionD(selectedQuestion.getOptionD());
				//设置解析
				paperDetailAndQuestion.setAnalysis(selectedQuestion.getAnalysis());
				//设置分值
				paperDetailAndQuestion.setGrade(selectedQuestion.getGrade());
				//设置题目类型
				paperDetailAndQuestion.setQuestionType(selectedQuestion.getQuestionType());
				autoSelectQuestionList.add(paperDetailAndQuestion);
			}else{
				int index = 0;
				while(true){
					index = new Random().nextInt(judgement3Length);
					int questionId = judgement3List.get(index).getQuestionId();
					int i = 0;
					for(i = 0;i<autoSelectQuestionList.size();i++){
						Paperdetail paperdetail = autoSelectQuestionList.get(i);
						if(paperdetail.getQuestionId() == questionId){
							break;
						}
					}
					if(i == autoSelectQuestionList.size()){
						break;
					}
				}
				QuestionVo selectedQuestion = judgement3List.get(index);
				PaperDetailAndQuestion paperDetailAndQuestion = new PaperDetailAndQuestion();
				//设置questionId
				paperDetailAndQuestion.setQuestionId(selectedQuestion.getQuestionId());
				//设置questionNo
				paperDetailAndQuestion.setQuestionNo(currentQuestionNo);
				//设置题目
				paperDetailAndQuestion.setQuestion(selectedQuestion.getQuestion());
				//设置正确答案
				paperDetailAndQuestion.setRightAnswer(selectedQuestion.getRightAnswer());
				//设置A选项
				paperDetailAndQuestion.setOptionA(selectedQuestion.getOptionA());
				//设置B选项
				paperDetailAndQuestion.setOptionB(selectedQuestion.getOptionB());
				//设置C选项
				paperDetailAndQuestion.setOptionC(selectedQuestion.getOptionC());
				//设置D选项
				paperDetailAndQuestion.setOptionD(selectedQuestion.getOptionD());
				//设置解析
				paperDetailAndQuestion.setAnalysis(selectedQuestion.getAnalysis());
				//设置分值
				paperDetailAndQuestion.setGrade(selectedQuestion.getGrade());
				//设置题目类型
				paperDetailAndQuestion.setQuestionType(selectedQuestion.getQuestionType());
				autoSelectQuestionList.add(paperDetailAndQuestion);
			}
			currentQuestionNo ++;
		}else{
			//首先选择一个随机的难度
			Integer randomCom = 0;
			if(judgementComplexity == 1){
				randomCom = 3;
			}else if(judgementComplexity == 2){
				randomCom = 1;
			}else if(judgementComplexity == 3){
				randomCom = 2;
			}
			int length = 0;
			while(judgementNumCopy-- > 0){
				//选择一道随机难度的题目
				if(randomCom == 1){
					int index = 0;
					while(true){
						index = new Random().nextInt(judgement1Length);
						int questionId = judgement1List.get(index).getQuestionId();
						int i = 0;
						for(i = 0;i<autoSelectQuestionList.size();i++){
							Paperdetail paperdetail = autoSelectQuestionList.get(i);
							if(paperdetail.getQuestionId() == questionId){
								break;
							}
						}
						if(i == autoSelectQuestionList.size()){
							break;
						}
					}
					QuestionVo selectedQuestion = judgement1List.get(index);
					PaperDetailAndQuestion paperDetailAndQuestion = new PaperDetailAndQuestion();
					//设置questionId
					paperDetailAndQuestion.setQuestionId(selectedQuestion.getQuestionId());
					//设置questionNo
					paperDetailAndQuestion.setQuestionNo(currentQuestionNo);
					//设置题目
					paperDetailAndQuestion.setQuestion(selectedQuestion.getQuestion());
					//设置正确答案
					paperDetailAndQuestion.setRightAnswer(selectedQuestion.getRightAnswer());
					//设置A选项
					paperDetailAndQuestion.setOptionA(selectedQuestion.getOptionA());
					//设置B选项
					paperDetailAndQuestion.setOptionB(selectedQuestion.getOptionB());
					//设置C选项
					paperDetailAndQuestion.setOptionC(selectedQuestion.getOptionC());
					//设置D选项
					paperDetailAndQuestion.setOptionD(selectedQuestion.getOptionD());
					//设置解析
					paperDetailAndQuestion.setAnalysis(selectedQuestion.getAnalysis());
					//设置分值
					paperDetailAndQuestion.setGrade(selectedQuestion.getGrade());
					//设置题目类型
					paperDetailAndQuestion.setQuestionType(selectedQuestion.getQuestionType());
					autoSelectQuestionList.add(paperDetailAndQuestion);
				}else if(randomCom == 2){
					int index = 0;
					while(true){
						index = new Random().nextInt(judgement2Length);
						int questionId = judgement2List.get(index).getQuestionId();
						int i = 0;
						for(i = 0;i<autoSelectQuestionList.size();i++){
							Paperdetail paperdetail = autoSelectQuestionList.get(i);
							if(paperdetail.getQuestionId() == questionId){
								break;
							}
						}
						if(i == autoSelectQuestionList.size()){
							break;
						}
					}
					QuestionVo selectedQuestion = judgement2List.get(index);
					PaperDetailAndQuestion paperDetailAndQuestion = new PaperDetailAndQuestion();
					//设置questionId
					paperDetailAndQuestion.setQuestionId(selectedQuestion.getQuestionId());
					//设置questionNo
					paperDetailAndQuestion.setQuestionNo(currentQuestionNo);
					//设置题目
					paperDetailAndQuestion.setQuestion(selectedQuestion.getQuestion());
					//设置正确答案
					paperDetailAndQuestion.setRightAnswer(selectedQuestion.getRightAnswer());
					//设置A选项
					paperDetailAndQuestion.setOptionA(selectedQuestion.getOptionA());
					//设置B选项
					paperDetailAndQuestion.setOptionB(selectedQuestion.getOptionB());
					//设置C选项
					paperDetailAndQuestion.setOptionC(selectedQuestion.getOptionC());
					//设置D选项
					paperDetailAndQuestion.setOptionD(selectedQuestion.getOptionD());
					//设置解析
					paperDetailAndQuestion.setAnalysis(selectedQuestion.getAnalysis());
					//设置分值
					paperDetailAndQuestion.setGrade(selectedQuestion.getGrade());
					//设置题目类型
					paperDetailAndQuestion.setQuestionType(selectedQuestion.getQuestionType());
					autoSelectQuestionList.add(paperDetailAndQuestion);
				}else{
					int index = 0;
					while(true){
						index = new Random().nextInt(judgement3Length);
						int questionId = judgement3List.get(index).getQuestionId();
						int i = 0;
						for(i = 0;i<autoSelectQuestionList.size();i++){
							Paperdetail paperdetail = autoSelectQuestionList.get(i);
							if(paperdetail.getQuestionId() == questionId){
								break;
							}
						}
						if(i == autoSelectQuestionList.size()){
							break;
						}
					}
					QuestionVo selectedQuestion = judgement3List.get(index);
					PaperDetailAndQuestion paperDetailAndQuestion = new PaperDetailAndQuestion();
					//设置questionId
					paperDetailAndQuestion.setQuestionId(selectedQuestion.getQuestionId());
					//设置questionNo
					paperDetailAndQuestion.setQuestionNo(currentQuestionNo);
					//设置题目
					paperDetailAndQuestion.setQuestion(selectedQuestion.getQuestion());
					//设置正确答案
					paperDetailAndQuestion.setRightAnswer(selectedQuestion.getRightAnswer());
					//设置A选项
					paperDetailAndQuestion.setOptionA(selectedQuestion.getOptionA());
					//设置B选项
					paperDetailAndQuestion.setOptionB(selectedQuestion.getOptionB());
					//设置C选项
					paperDetailAndQuestion.setOptionC(selectedQuestion.getOptionC());
					//设置D选项
					paperDetailAndQuestion.setOptionD(selectedQuestion.getOptionD());
					//设置解析
					paperDetailAndQuestion.setAnalysis(selectedQuestion.getAnalysis());
					//设置分值
					paperDetailAndQuestion.setGrade(selectedQuestion.getGrade());
					//设置题目类型
					paperDetailAndQuestion.setQuestionType(selectedQuestion.getQuestionType());
					autoSelectQuestionList.add(paperDetailAndQuestion);
				}
				currentQuestionNo ++;
				//次数加一
				length++;
				//如果次数为奇数
				if(length % 2 != 0){
					//改变随机难度题目
					randomCom = (randomCom+1) % 3;
					if(randomCom == 0) randomCom = 3;
				}
			}
		}
		//开始选择选择题
		Integer selectionNumCopy = selectionNum;
		int selectComplexity = autoSelectQuestion.getSelectionComplexity();
		int flag2 = selectComplexity;
		if(selectionNum == 1){
			//随机选择一个题目放进数组
			if(flag2 == 1){
				int index = 0;
				while(true){
					index = new Random().nextInt(selection1Length);
					int questionId = selecion1List.get(index).getQuestionId();
					int i = 0;
					for(i = 0;i<autoSelectQuestionList.size();i++){
						Paperdetail paperdetail = autoSelectQuestionList.get(i);
						if(paperdetail.getQuestionId() == questionId){
							break;
						}
					}
					if(i == autoSelectQuestionList.size()){
						break;
					}
				}
				QuestionVo selectedQuestion = selecion1List.get(index);
				PaperDetailAndQuestion paperDetailAndQuestion = new PaperDetailAndQuestion();
				//设置questionId
				paperDetailAndQuestion.setQuestionId(selectedQuestion.getQuestionId());
				//设置questionNo
				paperDetailAndQuestion.setQuestionNo(currentQuestionNo);
				//设置题目
				paperDetailAndQuestion.setQuestion(selectedQuestion.getQuestion());
				//设置正确答案
				paperDetailAndQuestion.setRightAnswer(selectedQuestion.getRightAnswer());
				//设置A选项
				paperDetailAndQuestion.setOptionA(selectedQuestion.getOptionA());
				//设置B选项
				paperDetailAndQuestion.setOptionB(selectedQuestion.getOptionB());
				//设置C选项
				paperDetailAndQuestion.setOptionC(selectedQuestion.getOptionC());
				//设置D选项
				paperDetailAndQuestion.setOptionD(selectedQuestion.getOptionD());
				//设置解析
				paperDetailAndQuestion.setAnalysis(selectedQuestion.getAnalysis());
				//设置分值
				paperDetailAndQuestion.setGrade(selectedQuestion.getGrade());
				//设置题目类型
				paperDetailAndQuestion.setQuestionType(selectedQuestion.getQuestionType());
				autoSelectQuestionList.add(paperDetailAndQuestion);
			}else if(flag2 == 2){
				int index = 0;
				while(true){
					index = new Random().nextInt(selection2Length);
					int questionId = selecion2List.get(index).getQuestionId();
					int i = 0;
					for(i = 0;i<autoSelectQuestionList.size();i++){
						Paperdetail paperdetail = autoSelectQuestionList.get(i);
						if(paperdetail.getQuestionId() == questionId){
							break;
						}
					}
					if(i == autoSelectQuestionList.size()){
						break;
					}
				}
				QuestionVo selectedQuestion = selecion2List.get(index);
				PaperDetailAndQuestion paperDetailAndQuestion = new PaperDetailAndQuestion();
				//设置questionId
				paperDetailAndQuestion.setQuestionId(selectedQuestion.getQuestionId());
				//设置questionNo
				paperDetailAndQuestion.setQuestionNo(currentQuestionNo);
				//设置题目
				paperDetailAndQuestion.setQuestion(selectedQuestion.getQuestion());
				//设置正确答案
				paperDetailAndQuestion.setRightAnswer(selectedQuestion.getRightAnswer());
				//设置A选项
				paperDetailAndQuestion.setOptionA(selectedQuestion.getOptionA());
				//设置B选项
				paperDetailAndQuestion.setOptionB(selectedQuestion.getOptionB());
				//设置C选项
				paperDetailAndQuestion.setOptionC(selectedQuestion.getOptionC());
				//设置D选项
				paperDetailAndQuestion.setOptionD(selectedQuestion.getOptionD());
				//设置解析
				paperDetailAndQuestion.setAnalysis(selectedQuestion.getAnalysis());
				//设置分值
				paperDetailAndQuestion.setGrade(selectedQuestion.getGrade());
				//设置题目类型
				paperDetailAndQuestion.setQuestionType(selectedQuestion.getQuestionType());
				autoSelectQuestionList.add(paperDetailAndQuestion);
			}else{
				int index = 0;
				while(true){
					index = new Random().nextInt(selection3Length);
					int questionId = selecion3List.get(index).getQuestionId();
					int i = 0;
					for(i = 0;i<autoSelectQuestionList.size();i++){
						Paperdetail paperdetail = autoSelectQuestionList.get(i);
						if(paperdetail.getQuestionId() == questionId){
							break;
						}
					}
					if(i == autoSelectQuestionList.size()){
						break;
					}
				}
				QuestionVo selectedQuestion = selecion3List.get(index);
				PaperDetailAndQuestion paperDetailAndQuestion = new PaperDetailAndQuestion();
				//设置questionId
				paperDetailAndQuestion.setQuestionId(selectedQuestion.getQuestionId());
				//设置questionNo
				paperDetailAndQuestion.setQuestionNo(currentQuestionNo);
				//设置题目
				paperDetailAndQuestion.setQuestion(selectedQuestion.getQuestion());
				//设置正确答案
				paperDetailAndQuestion.setRightAnswer(selectedQuestion.getRightAnswer());
				//设置A选项
				paperDetailAndQuestion.setOptionA(selectedQuestion.getOptionA());
				//设置B选项
				paperDetailAndQuestion.setOptionB(selectedQuestion.getOptionB());
				//设置C选项
				paperDetailAndQuestion.setOptionC(selectedQuestion.getOptionC());
				//设置D选项
				paperDetailAndQuestion.setOptionD(selectedQuestion.getOptionD());
				//设置解析
				paperDetailAndQuestion.setAnalysis(selectedQuestion.getAnalysis());
				//设置分值
				paperDetailAndQuestion.setGrade(selectedQuestion.getGrade());
				//设置题目类型
				paperDetailAndQuestion.setQuestionType(selectedQuestion.getQuestionType());
				autoSelectQuestionList.add(paperDetailAndQuestion);
			}
			currentQuestionNo ++;
		}else{
			//首先选择一个随机的难度
			Integer randomCom = 0;
			if(selectComplexity == 1){
				randomCom = 3;
			}else if(selectComplexity == 2){
				randomCom = 1;
			}else if(selectComplexity == 3){
				randomCom = 2;
			}
			int length = 0;
			while(selectionNumCopy-- > 0){
				//选择一道随机难度的题目
				if(randomCom == 1){
					int index = 0;
					while(true){
						index = new Random().nextInt(selection1Length);
						int questionId = selecion1List.get(index).getQuestionId();
						int i = 0;
						for(i = 0;i<autoSelectQuestionList.size();i++){
							Paperdetail paperdetail = autoSelectQuestionList.get(i);
							if(paperdetail.getQuestionId() == questionId){
								break;
							}
						}
						if(i == autoSelectQuestionList.size()){
							break;
						}
					}
					QuestionVo selectedQuestion = selecion1List.get(index);
					PaperDetailAndQuestion paperDetailAndQuestion = new PaperDetailAndQuestion();
					//设置questionId
					paperDetailAndQuestion.setQuestionId(selectedQuestion.getQuestionId());
					//设置questionNo
					paperDetailAndQuestion.setQuestionNo(currentQuestionNo);
					//设置题目
					paperDetailAndQuestion.setQuestion(selectedQuestion.getQuestion());
					//设置正确答案
					paperDetailAndQuestion.setRightAnswer(selectedQuestion.getRightAnswer());
					//设置A选项
					paperDetailAndQuestion.setOptionA(selectedQuestion.getOptionA());
					//设置B选项
					paperDetailAndQuestion.setOptionB(selectedQuestion.getOptionB());
					//设置C选项
					paperDetailAndQuestion.setOptionC(selectedQuestion.getOptionC());
					//设置D选项
					paperDetailAndQuestion.setOptionD(selectedQuestion.getOptionD());
					//设置解析
					paperDetailAndQuestion.setAnalysis(selectedQuestion.getAnalysis());
					//设置分值
					paperDetailAndQuestion.setGrade(selectedQuestion.getGrade());
					//设置题目类型
					paperDetailAndQuestion.setQuestionType(selectedQuestion.getQuestionType());
					autoSelectQuestionList.add(paperDetailAndQuestion);
				}else if(randomCom == 2){
					int index = 0;
					while(true){
						index = new Random().nextInt(selection2Length);
						int questionId = selecion2List.get(index).getQuestionId();
						int i = 0;
						for(i = 0;i<autoSelectQuestionList.size();i++){
							Paperdetail paperdetail = autoSelectQuestionList.get(i);
							if(paperdetail.getQuestionId() == questionId){
								break;
							}
						}
						if(i == autoSelectQuestionList.size()){
							break;
						}
					}
					QuestionVo selectedQuestion = selecion2List.get(index);
					PaperDetailAndQuestion paperDetailAndQuestion = new PaperDetailAndQuestion();
					//设置questionId
					paperDetailAndQuestion.setQuestionId(selectedQuestion.getQuestionId());
					//设置questionNo
					paperDetailAndQuestion.setQuestionNo(currentQuestionNo);
					//设置题目
					paperDetailAndQuestion.setQuestion(selectedQuestion.getQuestion());
					//设置正确答案
					paperDetailAndQuestion.setRightAnswer(selectedQuestion.getRightAnswer());
					//设置A选项
					paperDetailAndQuestion.setOptionA(selectedQuestion.getOptionA());
					//设置B选项
					paperDetailAndQuestion.setOptionB(selectedQuestion.getOptionB());
					//设置C选项
					paperDetailAndQuestion.setOptionC(selectedQuestion.getOptionC());
					//设置D选项
					paperDetailAndQuestion.setOptionD(selectedQuestion.getOptionD());
					//设置解析
					paperDetailAndQuestion.setAnalysis(selectedQuestion.getAnalysis());
					//设置分值
					paperDetailAndQuestion.setGrade(selectedQuestion.getGrade());
					//设置题目类型
					paperDetailAndQuestion.setQuestionType(selectedQuestion.getQuestionType());
					autoSelectQuestionList.add(paperDetailAndQuestion);
				}else{
					int index = 0;
					while(true){
						index = new Random().nextInt(selection3Length);
						int questionId = selecion3List.get(index).getQuestionId();
						int i = 0;
						for(i = 0;i<autoSelectQuestionList.size();i++){
							Paperdetail paperdetail = autoSelectQuestionList.get(i);
							if(paperdetail.getQuestionId() == questionId){
								break;
							}
						}
						if(i == autoSelectQuestionList.size()){
							break;
						}
					}
					QuestionVo selectedQuestion = selecion3List.get(index);
					PaperDetailAndQuestion paperDetailAndQuestion = new PaperDetailAndQuestion();
					//设置questionId
					paperDetailAndQuestion.setQuestionId(selectedQuestion.getQuestionId());
					//设置questionNo
					paperDetailAndQuestion.setQuestionNo(currentQuestionNo);
					//设置题目
					paperDetailAndQuestion.setQuestion(selectedQuestion.getQuestion());
					//设置正确答案
					paperDetailAndQuestion.setRightAnswer(selectedQuestion.getRightAnswer());
					//设置A选项
					paperDetailAndQuestion.setOptionA(selectedQuestion.getOptionA());
					//设置B选项
					paperDetailAndQuestion.setOptionB(selectedQuestion.getOptionB());
					//设置C选项
					paperDetailAndQuestion.setOptionC(selectedQuestion.getOptionC());
					//设置D选项
					paperDetailAndQuestion.setOptionD(selectedQuestion.getOptionD());
					//设置解析
					paperDetailAndQuestion.setAnalysis(selectedQuestion.getAnalysis());
					//设置分值
					paperDetailAndQuestion.setGrade(selectedQuestion.getGrade());
					//设置题目类型
					paperDetailAndQuestion.setQuestionType(selectedQuestion.getQuestionType());
					autoSelectQuestionList.add(paperDetailAndQuestion);
				}
				currentQuestionNo ++;
				//次数加一
				length++;
				//如果次数为奇数
				if(length % 2 != 0){
					//改变随机难度题目
					randomCom = (randomCom+1) % 3;
					if(randomCom == 0) randomCom = 3;
				}
			}
		}
		return autoSelectQuestionList;
	}


	/**
	 * 查询已经有成绩的试卷
	 * @param paper
	 * @return
	 */
	@Override
	public List<Paper> queryPaperListAlreadyScore(Paper paper) {
		List<Paper> paperListAlreadyScore = paperDao.selectPaperByAlreadyScore(paper);
		return paperListAlreadyScore;
	}


}
