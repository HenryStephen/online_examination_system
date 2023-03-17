package com.ssm.online_examination_system.mapper.manual;

import com.ssm.online_examination_system.po.Subject;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectDao {

	/**
	 * 根据选择返回科目
	 * @return
	 */
	List<Subject> selectBySelective(Subject subject);

}
