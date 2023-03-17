package com.ssm.online_examination_system.controller;

import com.github.pagehelper.PageInfo;
import com.ssm.online_examination_system.annotation.TeacherToken;
import com.ssm.online_examination_system.po.Paper;
import com.ssm.online_examination_system.po.Score;
import com.ssm.online_examination_system.service.PaperService;
import com.ssm.online_examination_system.service.ScoreService;
import com.ssm.online_examination_system.utils.LayUIResponse;
import com.ssm.online_examination_system.utils.ServerResponse;
import com.ssm.online_examination_system.vo.ScoreAndUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/score")
public class ScoreController {

	@Autowired
	private ScoreService scoreService;

	@Autowired
	private PaperService paperService;
	/**
	 * 查询学生成绩
	 * @param score
	 * @return
	 */
	@GetMapping("/query")
	public Score queryScore(Score score){
		log.info("查询学生成绩");
		Score scoreTemp = new Score();
		try{
			scoreTemp = scoreService.queryScore(score);
			log.info("查询学生成绩成功");
		}catch (Exception e){
			log.error("查询学生成绩失败");
		}
		return scoreTemp;
	}

	/**
	 * 成绩信息统计
	 * @param score
	 * @param PageSize
	 * @param PageNum
	 * @return
	 */
	@GetMapping("/queryList")
	@TeacherToken
	public LayUIResponse queryScoreList(Score score, @RequestParam("page") Integer PageSize, @RequestParam("limit") Integer PageNum){
		PageInfo<ScoreAndUser> pageInfo = null;
		LayUIResponse layUIResponse = new LayUIResponse();
		log.info("成绩信息统计");
		try{
			pageInfo = scoreService.queryScoreList(score,PageSize,PageNum);
			if(pageInfo != null){
				log.info("成绩信息统计成功");
				return layUIResponse.success(pageInfo);
			}
		}catch (Exception e){
			e.printStackTrace();
			log.error("成绩信息统计失败");
		}
		return layUIResponse;
	}

	@GetMapping("/analyze")
	@TeacherToken
	public ServerResponse analyzePaper(Score score){
		List<Score> scoreList = null;
		List<Integer> scoreNumList = new ArrayList<>();
		Integer[] scoreNum = null;
		log.info("分析试卷");
		try{
			scoreList = scoreService.queryScoreList(score);
			Paper paper = paperService.queryPaper(score.getPaperId());
			Integer questionTotalgrade = paper.getQuestionTotalgrade();
			if(scoreList != null && scoreList.size()>0){
				log.info("成绩信息统计成功");
				if(questionTotalgrade >= 10 ){
					int length = Integer.valueOf(questionTotalgrade /10);
					if(questionTotalgrade % 10 == 0){
						scoreNum = new Integer[length];
					}else{
						if(questionTotalgrade - length*10 >= 5){
							scoreNum = new Integer[length+1];
						}else{
							scoreNum = new Integer[length];
						}
					}
					for(int i=0;i<scoreNum.length;i++){
						scoreNum[i] = 0;
					}
				}else{
					scoreNum = new Integer[1];
					scoreNum[0] = 0;
				}
				//开始计算人数
				for(Score item : scoreList){
					//获取分数
					Integer userGrade = item.getScore();
					if(questionTotalgrade >= 10){
						if(userGrade % 10 == 0){
							int index = Integer.valueOf(userGrade/10);
							scoreNum[index-1]++;
						}else{
							int index = Integer.valueOf(userGrade/10);
							if(userGrade - index * 10 >= 5){
								scoreNum[index]++;
							}else{
								scoreNum[index-1]++;
							}
						}
					}else{
						scoreNum[0]++;
					}
				}
				return ServerResponse.createBySuccess("成绩信息统计成功",scoreNum);
			}
		}catch (Exception e){
			e.printStackTrace();
			log.error("成绩信息统计失败");
		}
		return ServerResponse.createByErrorMessage("成绩信息统计失败");
	}
}
