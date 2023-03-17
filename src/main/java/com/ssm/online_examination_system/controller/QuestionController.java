package com.ssm.online_examination_system.controller;

import com.github.pagehelper.PageInfo;
import com.ssm.online_examination_system.annotation.AuthToken;
import com.ssm.online_examination_system.annotation.TeacherToken;
import com.ssm.online_examination_system.po.Question;
import com.ssm.online_examination_system.service.QuestionService;
import com.ssm.online_examination_system.utils.LayUIResponse;
import com.ssm.online_examination_system.vo.QuestionVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AuthToken
@RequestMapping("/question")
public class QuestionController {

	@Autowired
	private QuestionService questionService;

	/**
	 * 查询题目信息
	 * @param questionVo
	 * @param PageSize
	 * @param PageNum
	 * @return
	 */
	@GetMapping("/queryInfo")
	@TeacherToken
	public LayUIResponse queryQuestion(QuestionVo questionVo, @RequestParam("page") Integer PageSize, @RequestParam("limit") Integer PageNum){
		PageInfo<QuestionVo> pageInfo = null;
		log.info("题库中题目信息的查找");
		try{
			pageInfo = questionService.queryQuestion(questionVo,PageSize,PageNum);
			log.info("查找题目信息成功");
			if(pageInfo != null){
				return LayUIResponse.success(pageInfo);
			}else{
				return LayUIResponse.success();
			}
		}catch (Exception e){
			log.error("题库中暂无该类型的题目");
		}
		return LayUIResponse.fail();
	}

	/**
	 * 修改题目信息
	 * @param question
	 * @return
	 */
	@GetMapping("/modifyInfo")
	public LayUIResponse modifyQuestionInfo(Question question){
		log.info("修改题目信息");
		try{
			boolean flag = questionService.modifyQuestionInfo(question);
			if(flag){
				log.info("修改题目信息成功");
				return LayUIResponse.success();
			}
		}catch(Exception e){
			log.error("修改题目信息出错");
		}
		return LayUIResponse.fail();
	}

	/**
	 * 删除题目
	 * @param questionId
	 * @return
	 */
	@GetMapping("/delete")
	public LayUIResponse deleteQuestion(@RequestParam("questionId") Integer questionId){
		log.info("删除题目");
		try{
			boolean flag = questionService.deleteQuestion(questionId);
			if(flag){
				log.info("删除题目成功");
				return LayUIResponse.success();
			}
		}catch (Exception e){
			log.error("删除题目失败");
		}
		return LayUIResponse.fail();
	}

	/**
	 * 添加题目
	 * @param question
	 * @return
	 */
	@GetMapping("/add")
	public LayUIResponse addQuestion(Question question){
		log.info("添加题目");
		try{
			boolean flag = questionService.addQuestion(question);
			if(flag){
				log.info("添加题目成功");
				return LayUIResponse.success();
			}
		}catch (Exception e){
			log.error("添加题目失败");
		}
		return LayUIResponse.fail();
	}
}
