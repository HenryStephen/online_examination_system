package com.ssm.online_examination_system.service;

import com.ssm.online_examination_system.po.Subject;

import java.util.List;

public interface SubjectService {

	/**
	 * 添加科目
	 * @param subject
	 * @return
	 */
	boolean addSubject(Subject subject);

	/**
	 * 删除科目
	 * @param subjectId
	 * @return
	 */
	boolean deleteSubject(Integer subjectId);

	/**
	 * 修改科目
	 * @param subject
	 * @return
	 */
	boolean updateSubject(Subject subject);

	/**
	 * 查找科目
	 * @param subjectId
	 * @return
	 */
	Subject querySubject(Integer subjectId);

	/**
	 * 查找科目集合
	 * @return
	 */
	List<Subject> querySubjectList(Subject subject);

}
