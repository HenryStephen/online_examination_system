package com.ssm.online_examination_system.service.impl;

import com.ssm.online_examination_system.mapper.auto.QuestionMapper;
import com.ssm.online_examination_system.mapper.auto.ScoreMapper;
import com.ssm.online_examination_system.mapper.auto.SubjectMapper;
import com.ssm.online_examination_system.mapper.auto.UsersMapper;
import com.ssm.online_examination_system.mapper.manual.HistoryQuestionDao;
import com.ssm.online_examination_system.mapper.manual.PaperDao;
import com.ssm.online_examination_system.mapper.manual.UsersDao;
import com.ssm.online_examination_system.po.*;
import com.ssm.online_examination_system.service.StudentAnswersService;
import com.ssm.online_examination_system.vo.HistoryQuestionVo;
import com.ssm.online_examination_system.vo.StudentInPaperVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @Author lowry
 * @CreateDate 2019/8/24 15:32
 * @Version 1.0
 */
@Service
public class StudentAnswersServiceImpl implements StudentAnswersService {
    @Resource
    private PaperDao paperDao;
    @Resource
    private UsersDao usersDao;
    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private HistoryQuestionDao historyQuestionDao;
    @Resource
    private SubjectMapper subjectMapper;
    @Resource
    private UsersMapper usersMapper;
    @Autowired
    private ScoreMapper scoreMapper;
    /**
     * 查看试卷基本信息
     * @param
     * @return
     */
    @Override
    public StudentInPaperVo showPaper(StudentInPaperVo studentInPaperVo) {
        StudentInPaperVo studentInPaperVo1 = paperDao.selectByPaperId(studentInPaperVo);
        if(studentInPaperVo1!=null)
        return studentInPaperVo1;
        else return null;
    }

    /**
     * 根据用户名返回完整的用户信息
     * @param users
     * @return
     */
    @Override
    public Users returnUsers(Users users) {
        Users users1 = usersDao.selectUserByUserName(users);
        if(users1==null)
            return null;
        else
            return users1;
    }

    /**
     * 判断用户密码
     * @param studentInPaperVo
     * @return
     */
    @Override
    public boolean ifPassWord(StudentInPaperVo studentInPaperVo) {
        StudentInPaperVo studentInPaperVo1=paperDao.ifPassword(studentInPaperVo);
        if(studentInPaperVo1==null)
            return false;
        else
            return  true;
    }

    /**
     *显示所有本用户查看到的试卷信息
     * @author luojiarui
     * @return
     */
    @Override
    public List<StudentInPaperVo> selectAllList(Users users) {
        List<StudentInPaperVo> newlist=new ArrayList<>();
        List<StudentInPaperVo> list = paperDao.selectAllList();
        List<StudentInPaperVo> newListNo = new ArrayList<>();
        //不显示非本校的试卷
        if(list != null && list.size() != 0){
            for(StudentInPaperVo item : list){
                if(item.getPaperLevel()==2){
                    if(!users.getUserAcademy().equals(item.getUserAcademy())){
                        continue;
                    }
                }
                newlist.add(item);
            }
        }
        //获取学生的ID
        Integer userId = users.getUserId();
        //不显示已经做过的试卷
        if(newlist!=null && newlist.size()!=0){
            for(StudentInPaperVo item : newlist){
                ScoreKey scoreKey = new ScoreKey();
                scoreKey.setPaperId(item.getPaperId());
                scoreKey.setUserId(userId);
                Score score = scoreMapper.selectByPrimaryKey(scoreKey);
                if(score == null){
                    newListNo.add(item);
                }
            }
        }
        if(newListNo!=null && newlist.size()>0)
            return newListNo;
        else return null;
    }

    /**
     * 查看题目
     * @param questionId
     * @return
     */
    @Override
    public Question selectQuestion(Integer questionId) {
        Question question = questionMapper.selectByPrimaryKey(questionId);
        if(question==null)
            return null;
        else
            return question;
    }

    /**
     * 将所有的答题情况插入表
     * @param historyQuestionVo
     * @return
     */
    @Override
    public boolean insertQuestion(HistoryQuestionVo historyQuestionVo) {
        int a = historyQuestionDao.insertHistoryQuestion(historyQuestionVo);
        if(a==0)
            return false;
        else return  true;
    }

    /**
     * 统计所有的分数
     * @param list
     * @return
     */
    @Override
    public int countAllQuestion(HistoryQuestionVo[] list) {
        int allNum = 0;
        for(HistoryQuestionVo item : list){
            HistoryQuestionVo historyQuestionVo = historyQuestionDao.selectAllGrade(item);
            if(historyQuestionVo != null)
                allNum += historyQuestionVo.getGrade();
        }
        return allNum;
    }

    /**
     * 获取当前答题的详情，题目信息
     * @author luojiarui
     * @param historyQuestionVo
     * @return
     */
    @Override
    public List<HistoryQuestionVo> getList(HistoryQuestionVo historyQuestionVo) {
       List<HistoryQuestionVo> list = historyQuestionDao.getHistoryQuestionList(historyQuestionVo);
       for(HistoryQuestionVo item : list){
           Integer subjectId = item.getSubjectId();
           //调用dao层查看学科名称
           Subject subject = subjectMapper.selectByPrimaryKey(subjectId);
           item.setSubjectName(subject.getSubjectName());
       }
        if(list!=null && list.size()!=0){
            Collections.sort(list, new Comparator<HistoryQuestionVo>() {
                @Override
                public int compare(HistoryQuestionVo o1, HistoryQuestionVo o2) {
                    if(o1.getQuestionNo()<o2.getQuestionNo()) {
                        //return -1:即为正序排序
                        return -1;
                    }else if (o1.getQuestionNo() == o2.getQuestionNo()) {
                        return 0;
                    }else {
                        //return 1: 即为倒序排序
                        return 1;
                    }
                }
            });
        }
       if(list== null && list.size()==0)
        return null;
       else return list;
    }

    /**
     * 获取历史试卷的基本信息
     * @param users
     * @return
     */
    @Override
    public List<StudentInPaperVo> getStudentInPaperVoList(Users users) {
        //根据usersid 获取历史答题paperid
        List<StudentInPaperVo> studentInPaperVoList =new ArrayList<>();
        List<Paper> list = historyQuestionDao.getHistoryPaperList(users);
        //根据获取到的list进行试卷的遍历
        for(Paper item : list){
            StudentInPaperVo studentInPaperVo = new StudentInPaperVo();
            studentInPaperVo.setPaperId(item.getPaperId());
            StudentInPaperVo studentInPaperVo1 = paperDao.selectByPaperId(studentInPaperVo);
            studentInPaperVoList.add(studentInPaperVo1);
        }
        for(StudentInPaperVo item : studentInPaperVoList){
            Users users1 =usersMapper.selectByPrimaryKey(item.getPaperSetterId());
            item.setUserName(users1.getUserName());
            Subject subject = subjectMapper.selectByPrimaryKey(item.getSubjectId());
            item.setSubjectName(subject.getSubjectName());
        }
        if(studentInPaperVoList == null && studentInPaperVoList.size() == 0)
            return null;
        else
            return studentInPaperVoList;
    }
}
