package com.ssm.online_examination_system.mapper.manual;

import com.ssm.online_examination_system.vo.QuestionVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao {

	/**
	 * 根据选择查询题目
	 * @param questionVo
	 * @return
	 */
	List<QuestionVo> selectBySelective(QuestionVo questionVo);

}
