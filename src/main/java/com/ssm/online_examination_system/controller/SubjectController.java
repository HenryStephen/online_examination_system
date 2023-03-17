package com.ssm.online_examination_system.controller;

import com.ssm.online_examination_system.annotation.AuthToken;
import com.ssm.online_examination_system.annotation.TeacherToken;
import com.ssm.online_examination_system.po.Subject;
import com.ssm.online_examination_system.service.SubjectService;
import com.ssm.online_examination_system.utils.LayUIResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@AuthToken
@RequestMapping("/subject")
public class SubjectController {

	@Autowired
	private SubjectService subjectService;

	/**
	 * 添加科目
	 * @param subject
	 * @return
	 */
	@GetMapping("/add")
	public LayUIResponse addSubject(Subject subject){
		log.info("添加科目");
		try{
			boolean flag = subjectService.addSubject(subject);
			if(flag){
				log.info("添加科目成功");
				return LayUIResponse.success();
			}
		}catch(Exception e){
			log.error("添加科目失败");
		}
		return LayUIResponse.fail();
	}

	/**
	 * 删除科目
	 * @param subjectId
	 * @return
	 */
	@GetMapping("/delete")
	public LayUIResponse deleteSubject(@RequestParam("subjectId") int subjectId){
		log.info("删除科目");
		try{
			boolean flag = subjectService.deleteSubject(subjectId);
			if(flag){
				log.info("删除科目成功");
				return LayUIResponse.success();
			}
		}catch (Exception e){
			log.error("删除科目失败");
		}
		return LayUIResponse.fail();
	}

	/**
	 * 修改科目
	 * @param subject
	 * @return
	 */
	@GetMapping("/update")
	public LayUIResponse updateSubject(Subject subject){
		log.info("修改科目");
		try {
			boolean flag = subjectService.updateSubject(subject);
			if(flag){
				log.info("修改科目成功");
				return LayUIResponse.success();
			}
		}catch (Exception e){
			log.error("修改科目失败");
		}
		return LayUIResponse.fail();
	}

	/**
	 * 查找科目
	 * @param subjectId
	 * @return
	 */
	@GetMapping("/query")
	@TeacherToken
	public Subject querySubject(@RequestParam("subjectId") int subjectId){
		log.info("查找科目");
		Subject subject = null;
		try{
			subject = subjectService.querySubject(subjectId);
			if(subject != null){
				log.info("查找科目成功");
			}
		}catch (Exception e){
			log.error("没有该科目");
		}
		return subject;
	}

	/**
	 * 查找科目列表
	 * @return
	 */
	@GetMapping("/queryList")
	@TeacherToken
	public List<Subject> querySubjectList(){
		log.info("查找科目列表");
		List<Subject> subjectList = null;
		try{
			subjectList = subjectService.querySubjectList(null);
			if(subjectList != null){
				log.info("查找科目列表成功");
			}
		}catch (Exception e){
			log.error("科目列表中没有信息");
		}
		return subjectList;
	}
}
