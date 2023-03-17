package com.ssm.online_examination_system.mapper.manual;

import com.ssm.online_examination_system.vo.PaperDetailAndQuestion;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaperdetailDao {

    /**
     * 根据paperId删除试卷详情
     * @param paperId
     * @return
     */
    int deleteByPaperId(Integer paperId);

    /**
     * 根据paperId查询试卷详情和题目
     * @param paperId
     * @return
     */
    List<PaperDetailAndQuestion> selectPaperDetailAndQuestionByPaperId(int paperId);
}