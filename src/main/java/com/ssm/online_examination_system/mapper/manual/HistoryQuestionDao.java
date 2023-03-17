package com.ssm.online_examination_system.mapper.manual;

import com.ssm.online_examination_system.po.Paper;
import com.ssm.online_examination_system.po.Users;
import com.ssm.online_examination_system.vo.HistoryQuestionVo;

import java.util.List;

/**
 * @Author lowry
 * @CreateDate 2019/8/25 0:52
 * @Version 1.0
 */
public interface HistoryQuestionDao {
    /**
     * 插入数据
     */
    int insertHistoryQuestion(HistoryQuestionVo historyQuestionVo);
    /**
     * 查询正确答案的所有分数信息
     * @author luojiarui
     */
    HistoryQuestionVo selectAllGrade(HistoryQuestionVo historyQuestionVo);
    /**
     * 查看所有的历史答题情况
     * @author luojiarui
     */
    List<HistoryQuestionVo> getHistoryQuestionList(HistoryQuestionVo historyQuestionVo);
    /**
     * 获取历史试卷id
     */
    List<Paper> getHistoryPaperList(Users users);
}
