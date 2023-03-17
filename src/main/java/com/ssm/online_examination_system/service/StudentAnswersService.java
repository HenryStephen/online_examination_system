package com.ssm.online_examination_system.service;

import com.ssm.online_examination_system.po.Question;
import com.ssm.online_examination_system.po.Users;
import com.ssm.online_examination_system.vo.HistoryQuestionVo;
import com.ssm.online_examination_system.vo.StudentInPaperVo;

import java.util.List;

/**
 * @Author lowry
 * @CreateDate 2019/8/24 15:30
 * @Version 1.0
 */
public interface StudentAnswersService {
    /**
     * 查看试卷基本信息
     * @author luojiauri
     */
    StudentInPaperVo  showPaper(StudentInPaperVo studentInPaperVo);
    /**
     * 根据用户名返回
     * @author luojiarui
     */
    Users returnUsers(Users users);
    /**
     * 进行试卷密码的验证
     * @author luojiarui
     */
    boolean ifPassWord(StudentInPaperVo studentInPaperVo);
    /**
     * 读者查看所有试卷
     * @author luojiarui
     */
    List<StudentInPaperVo> selectAllList(Users users);
    /**
     * 判断答题情况插入历史信息
     * @author luojiarui
     */
    Question selectQuestion(Integer questionId);
    /**
     * 将数据插入进去
     * @author luojiarui
     */
    boolean insertQuestion(HistoryQuestionVo historyQuestionVo);
    /**
     * 统计所有的分数
     * @author luojiarui
     */
    int countAllQuestion(HistoryQuestionVo[] list);
    /**
     * 查看当前试卷的做题情况
     * @author luojiarui
     */
    List<HistoryQuestionVo> getList(HistoryQuestionVo historyQuestionVo);
    /**
     * 获取历史试卷信息
     * @author luojiarui
     */
    List<StudentInPaperVo> getStudentInPaperVoList(Users users);
}
