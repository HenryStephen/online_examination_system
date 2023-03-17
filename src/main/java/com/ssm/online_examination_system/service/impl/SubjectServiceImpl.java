package com.ssm.online_examination_system.service.impl;

import com.ssm.online_examination_system.mapper.auto.SubjectMapper;
import com.ssm.online_examination_system.mapper.manual.SubjectDao;
import com.ssm.online_examination_system.po.Subject;
import com.ssm.online_examination_system.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {

	@Autowired
	private SubjectMapper subjectMapper;

	@Autowired
	private SubjectDao subjectDao;

	/**
	 * 添加科目
	 * @param subject
	 * @return
	 */
	@Override
	public boolean addSubject(Subject subject) {
		int count = subjectMapper.insertSelective(subject);
		if(count>0){
			return true;
		}
		return false;
	}

	/**
	 * 删除科目
	 * @param subjectId
	 * @return
	 */
	@Override
	public boolean deleteSubject(Integer subjectId) {
		int count = subjectMapper.deleteByPrimaryKey(subjectId);
		if(count>0){
			return true;
		}
		return false;
	}

	/**
	 * 修改科目
	 * @param subject
	 * @return
	 */
	@Override
	public boolean updateSubject(Subject subject) {
		int count = subjectMapper.updateByPrimaryKey(subject);
		if(count > 0){
			return true;
		}
		return false;
	}

	/**
	 * 查找科目
	 * @param subjectId
	 * @return
	 */
	@Override
	public Subject querySubject(Integer subjectId) {
		Subject subject = subjectMapper.selectByPrimaryKey(subjectId);
		return subject;
	}

	/**
	 * 查找科目集合
	 * @return
	 */
	@Override
	public List<Subject> querySubjectList(Subject subject) {
		return subjectDao.selectBySelective(subject);
	}

}
