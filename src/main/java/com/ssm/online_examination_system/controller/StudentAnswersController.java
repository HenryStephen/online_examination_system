package com.ssm.online_examination_system.controller;

import com.ssm.online_examination_system.annotation.StudentToken;
import com.ssm.online_examination_system.po.Question;
import com.ssm.online_examination_system.po.Score;
import com.ssm.online_examination_system.po.Users;
import com.ssm.online_examination_system.service.ScoreService;
import com.ssm.online_examination_system.service.StudentAnswersService;
import com.ssm.online_examination_system.utils.ServerResponse;
import com.ssm.online_examination_system.vo.HistoryQuestionVo;
import com.ssm.online_examination_system.vo.StudentInPaperVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author lowry
 * @CreateDate 2019/8/24 15:24
 * @Version 1.0
 */
@Slf4j
@RestController
@StudentToken
@RequestMapping("/student")
public class StudentAnswersController {

    @Resource
    private StudentAnswersService studentAnswersService;

    @Autowired
    private ScoreService scoreService;

    /**
     * 显示试卷基本信息
     * @author luojiarui
     */
    @RequestMapping(value = "/showPaper",method = RequestMethod.POST)
    public ServerResponse selectPaper(StudentInPaperVo studentInPaperVo, HttpServletRequest request){
        log.info("获取试卷基本信息");
        StudentInPaperVo studentInPaperVo1=null;
        try{
             studentInPaperVo1 = studentAnswersService.showPaper(studentInPaperVo);
             if(studentInPaperVo1==null)
                 return ServerResponse.createByErrorMessage("获取数据失败");
             String adminName = request.getHeader("userName");
             Users users1 = new Users();
             users1.setUserName(adminName);
             Users users = studentAnswersService.returnUsers(users1);
             if(studentInPaperVo1.getPaperLevel()==2){
                if(!users1.getUserAcademy().equals(studentInPaperVo1.getUserAcademy())){
                   return ServerResponse.createByErrorMessage("您不是本校的学生,无法访问");
                }
             }
        }catch (Exception e){
            log.error("服务器出错",e);
        }
        return ServerResponse.createBySuccess("获取数据成功",studentInPaperVo1);
    }

    /**
     * 判断密码是否正确 或者是否为本校
     * @author luojiarui
     */
    @PostMapping("/ifPassword")
    public ServerResponse ifRight(StudentInPaperVo studentInPaperVo){
        log.info("用户输入试卷密码验证");
        try{
            boolean b = studentAnswersService.ifPassWord(studentInPaperVo);
            if(b==false){
                return ServerResponse.createByErrorMessage("密码验证错误");
            }
        }catch (Exception e){
            log.error("服务器出错",e);
        }
        return ServerResponse.createBySuccessMessage("密码验证成功");
    }

    /**
     * 学生查看所有试卷信息
     * @author luojiarui
     */
    @RequestMapping(value = "/selectAllPapers")
    public ServerResponse selectAllPapers(HttpServletRequest request){
        log.info("学生查看所有试卷");
        List<StudentInPaperVo> list =null;
        try{
            String userName = request.getHeader("studentName");
            Users users1 = new Users();
            users1.setUserName(userName);
            Users users = studentAnswersService.returnUsers(users1);
            list = studentAnswersService.selectAllList(users);
            if(list == null && list.size() == 0)
                return ServerResponse.createByErrorMessage("试卷数据返回失败");
        }catch (Exception e){
            log.error("服务器出错",e);
        }
        return ServerResponse.createBySuccess("试卷数据返回成功",list);
    }

    /**
     * 提交试卷题目
     * 将试卷分数提交给前端
     * @author luojiarui
     */
    @RequestMapping(value = "/postPaper",method = RequestMethod.POST)
    public ServerResponse postPaperPresent(@RequestBody HistoryQuestionVo[] historyQuestionVoArray, HttpServletRequest request){
        log.info("学生做完试卷查看答题情况");
        Integer allNum = 0;
        try{
            String adminName = request.getHeader("studentName");
            Users users1 = new Users();
            users1.setUserName(adminName);
            //找到用户
            Users users = studentAnswersService.returnUsers(users1);
            for(int i = 0;i<historyQuestionVoArray.length;i++){
                HistoryQuestionVo item = historyQuestionVoArray[i];
                //设置用户Id
                item.setUserId(users.getUserId());
                //获取题目Id
                int questionId = item.getQuestionId();
                //根据题目Id查询question
                Question question = studentAnswersService.selectQuestion(questionId);
                //设置是否正确字段
                if(question.getRightAnswer().equals(item.getUserQuestion())){
                    item.setIsRight(2);
                }else{
                    item.setIsRight(1);
                }
            }
            //将所有的答题情况插入表里面
            for(HistoryQuestionVo item : historyQuestionVoArray){
                boolean b = studentAnswersService.insertQuestion(item);
                if(b == false)
                    return ServerResponse.createByErrorMessage("数据插入失败");
            }
            //在service层统计分数
            allNum = studentAnswersService.countAllQuestion(historyQuestionVoArray);
            Score score = new Score();
            score.setUserId(users.getUserId());
            score.setPaperId(historyQuestionVoArray[0].getPaperId());
            score.setScore(allNum);
            boolean b = scoreService.insertScore(score);
            if(b == false){
                return ServerResponse.createByErrorMessage("数据插入失败");
            }
        }catch (Exception e){
            log.error("服务器出错",e);
        }
        return ServerResponse.createBySuccess("分数返回成功",allNum);
    }

    /**
     *查看答题详情
     * @author luojiarui
     */
    @PostMapping("/seeHistoryAnswers")
    public ServerResponse seeHistoryAnswers(HistoryQuestionVo historyQuestionVo,HttpServletRequest request){
       log.info("查看答题详情");
       List<HistoryQuestionVo> list =null;
       try{
           String adminName = request.getHeader("studentName");
           Users users1 = new Users();
           users1.setUserName(adminName);
           Users users = studentAnswersService.returnUsers(users1);
           historyQuestionVo.setUserId(users.getUserId());
           //获取当前的所有题目集合
           list = studentAnswersService.getList(historyQuestionVo);
           if(list==null)
               return ServerResponse.createByErrorMessage("获取答题详情失败");
       }catch (Exception e){
           log.error("服务器出错",e);
       }
       return ServerResponse.createBySuccess("获取当前答题详情成功",list);
    }

    /**
     * 历史答题详情
     * @author luojiarui
     */
    @RequestMapping(value = "/selectHistoryPaper",method = RequestMethod.POST)
    public ServerResponse selectAllDonePaper(HttpServletRequest request){
        log.info("查看历史答题试卷信息");
        List<StudentInPaperVo> list = null;
        try{
            String adminName = request.getHeader("studentName");
            Users users1 = new Users();
            users1.setUserName(adminName);
            Users users = studentAnswersService.returnUsers(users1);
            //根据userid获取历史试卷信息
            list=studentAnswersService.getStudentInPaperVoList(users);
            if(list==null)
                return ServerResponse.createByErrorMessage("获取数据失败");
        }catch (Exception e){
            log.error("服务器出错",e);
        }
        return ServerResponse.createBySuccess("获取数据成功",list);
    }

    /**
     * 根据试卷Id和学生Id获得分数
     * @param request
     * @return
     */
    @GetMapping("/selectScoreBypaperId")
    public Score selectScoreBypaperId(@RequestParam("paperId") Integer paperId, HttpServletRequest request){
        log.info("根据试卷Id和学生Id获得分数");
        try{
            Score score = new Score();
            //设置paperId
            score.setPaperId(paperId);
            String adminName = request.getHeader("studentName");
            Users users = new Users();
            users.setUserName(adminName);
            users = studentAnswersService.returnUsers(users);
            score.setUserId(users.getUserId());
            score = scoreService.queryScore(score);
            if(score.getScore() != null){
                return score;
            }
        }catch (Exception e){
            log.error("根据试卷Id和学生Id获得分数失败");
        }
        return null;
    }
}
