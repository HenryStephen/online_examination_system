package com.ssm.online_examination_system.mapper.manual;

import com.ssm.online_examination_system.po.Paper;
import com.ssm.online_examination_system.vo.StudentInPaperVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaperDao {

	/**
	 * 根据选择查找试卷
	 * @param paper
	 * @return
	 */
	List<Paper> selectBySelective(Paper paper);

	/**
	 * 查询已经有成绩的试卷
	 * @return
	 */
	List<Paper> selectPaperByAlreadyScore(Paper paper);

	/**
	 * 返回试卷详细信息
	 * @author luojiarui
	 */
	StudentInPaperVo selectByPaperId(StudentInPaperVo studentInPaperVo);

	/**
	 * 判断试卷输入密码是否正确
	 * @author luojiarui
	 */
	StudentInPaperVo ifPassword(StudentInPaperVo studentInPaperVo);

	/**
	 * 获取所有试卷
	 */
	List<StudentInPaperVo> selectAllList();
}
