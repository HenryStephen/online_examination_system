package com.ssm.online_examination_system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ssm.online_examination_system.annotation.StudentToken;
import com.ssm.online_examination_system.annotation.TeacherToken;
import com.ssm.online_examination_system.po.Paper;
import com.ssm.online_examination_system.po.Paperdetail;
import com.ssm.online_examination_system.po.Users;
import com.ssm.online_examination_system.service.PaperService;
import com.ssm.online_examination_system.service.PaperdetailService;
import com.ssm.online_examination_system.service.StudentAnswersService;
import com.ssm.online_examination_system.service.UsersService;
import com.ssm.online_examination_system.utils.LayUIResponse;
import com.ssm.online_examination_system.utils.ServerResponse;
import com.ssm.online_examination_system.vo.AutoSelectQuestion;
import com.ssm.online_examination_system.vo.PaperDetailAndQuestion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Slf4j
@TeacherToken
@RequestMapping("/paper")
public class PaperController {

	@Autowired
	private PaperService paperService;

	@Autowired
	private PaperdetailService paperdetailService;

	@Autowired
	private UsersService usersService;

	@Autowired
	private StudentAnswersService studentAnswersService;

	/**
	 * 添加试卷
	 * @param paperAndDetail
	 * @return
	 */
	@PostMapping("/createPaper")
	public ServerResponse addPaper(@RequestBody String paperAndDetail, HttpServletRequest request){
		log.info("添加试卷详情");
		try{
			//将字符串转换成json对象
			JSONObject jsonObject = JSONObject.parseObject(paperAndDetail);
			//获取试卷的基本信息
			JSONObject paperJsonObject = jsonObject.getJSONObject("paper");
			//获取试卷详情
			JSONArray paperDetailJsonArray = jsonObject.getJSONArray("paperdetailList");
			//生成试卷实体类
			Paper paper = JSON.toJavaObject(paperJsonObject,Paper.class);
			//根据userName找到user
			String userName = request.getHeader("teacherName");
			Users users = new Users();
			users.setUserName(userName);
			users = studentAnswersService.returnUsers(users);
			//设置试卷的出题人
			paper.setPaperSetterId(users.getUserId());
			//添加试卷，返回试卷的id
			paperService.addPaper(paper);
			Integer paperId = paper.getPaperId();
			//遍历试卷详情数组
			for(int i=0;i<paperDetailJsonArray.size();i++){
				Paperdetail paperdetail = JSON.toJavaObject(paperDetailJsonArray.getJSONObject(i),Paperdetail.class);
				paperdetail.setPaperId(paperId);
				paperdetailService.addPaperDeatil(paperdetail);
			}
			log.info("添加试卷成功");
			return ServerResponse.createBySuccess("创建试卷成功",paperId);
		}catch(Exception e){
			e.printStackTrace();
			log.error("添加试卷失败");
		}
		return ServerResponse.createByErrorMessage("创建试卷失败");
	}

	/**
	 * 删除试卷
	 * @param paperId
	 * @return
	 */
	@GetMapping("/delete")
	public LayUIResponse deletePaper(@RequestParam("paperId") Integer paperId){
		log.info("删除试卷");
		try{
			boolean flag = paperdetailService.deletePaperDetail(paperId);
			flag = paperService.deletePaper(paperId) && flag;
			if(flag){
				log.info("删除试卷成功");
				return LayUIResponse.success();
			}
		}catch (Exception e){
			log.error("删除试卷失败");
		}
		return LayUIResponse.fail();
	}

	/**
	 * 修改试卷
	 * @param paperString
	 * @return
	 */
	@PostMapping("/update")
	public LayUIResponse updatePaper(@RequestBody String paperString){
		log.info("修改试卷成功");
		try {
			JSONObject paperJsonObject = JSONObject.parseObject(paperString);
			Paper paper = JSON.toJavaObject(paperJsonObject,Paper.class);
			Integer paperId = paper.getPaperId();
			List<Paper> paperList = paperService.queryPaperList(null);
			for(Paper item : paperList){
				String paperName = item.getPaperName();
				if(paperName.equals(paper.getPaperName())&&(paperId != item.getPaperId())){
					return LayUIResponse.fail();
				}
			}
			boolean flag = paperService.updatePaper(paper);
			if(flag){
				log.info("修改试卷成功");
				return LayUIResponse.success();
			}
		}catch (Exception e){
			log.error("修改试卷失败");
		}
		return LayUIResponse.fail();
	}

	/**
	 * 查找试卷
	 * @param paperId
	 * @return
	 */
	@GetMapping("/query")
	public Paper queryPaper(@RequestParam("paperId") Integer paperId){
		log.info("查找试卷");
		Paper paper = null;
		try{
			paper = paperService.queryPaper(paperId);
			if(paper != null){
				log.info("查找试卷成功");
			}
		}catch (Exception e){
			log.error("没有该试卷");
		}
		return paper;
	}

	/**
	 *
	 * @return
	 */
	@GetMapping("/queryList")
	public LayUIResponse queryPaperList(Paper paper, HttpServletRequest request){
		log.info("查找试卷列表");
		List<Paper> paperList = null;
		try{
			String userName = request.getHeader("teacherName");
			Users users = new Users();
			users.setUserName(userName);
			users = usersService.queryUserInfo(users);//找到user
			paper.setPaperSetterId(users.getUserId());
			paperList = paperService.queryPaperList(paper);
			if(paperList != null && paperList.size()!=0){
				log.info("查找试卷列表成功");
				return LayUIResponse.success(paperList);
			}
		}catch (Exception e){
			log.error("试卷列表中没有该试卷");
		}
		return LayUIResponse.fail();
	}

	/**
	 * 查看试卷详情和题目
	 * @param paperId
	 * @return
	 */
	@GetMapping("/queryPaperDetailAndQuestion")
	@StudentToken
	public LayUIResponse queryPaperDetailAndQuestion(@RequestParam("paperId") Integer paperId){
		log.info("查看试卷详情和题目");
		List<PaperDetailAndQuestion> paperDetailAndQuestionList = null;
		try{
			paperDetailAndQuestionList = paperdetailService.selectPaperDetailAndQuestionByPaperId(paperId);
			if(paperDetailAndQuestionList != null){
				log.info("查看试卷详情和题目成功");
				return LayUIResponse.success(paperDetailAndQuestionList);
			}
		}catch (Exception e){
			log.error("查看试卷详情和题目失败");

		}
		return LayUIResponse.fail();
	}

	@GetMapping("/queryExamDuration")
	@StudentToken
	public ServerResponse queryExamDuration(@RequestParam("paperId") Integer paperId){
		log.info("查看试卷的时长");
		Paper paper = null;
		try{
			paper = paperService.queryPaper(paperId);
			if(paper != null){
				log.info("查看试卷总时长成功");
				return ServerResponse.createBySuccess("查看试卷总时长成功",paper.getExamDuration());
			}
		}catch (Exception e){
			log.error("查看试卷总时长失败");
		}
		return ServerResponse.createByErrorMessage("查看试卷总时长失败");
	}

	/**
	 * 查询已经有成绩的试卷
	 * @param request
	 * @return
	 */
	@GetMapping("/queryPaperListAlreadyScore")
	public LayUIResponse queryPaperListAlreadyScore(HttpServletRequest request){
		log.info("查找已经有成绩的试卷列表");
		List<Paper> paperListAlreadyScore = null;
		try{
			String userName = request.getHeader("teacherName");
			Users users = new Users();
			users.setUserName(userName);
			users = usersService.queryUserInfo(users);//找到user
			Paper paper = new Paper();
			paper.setPaperSetterId(users.getUserId());
			paperListAlreadyScore = paperService.queryPaperListAlreadyScore(paper);
			if(paperListAlreadyScore != null && paperListAlreadyScore.size()!=0){
				log.info("查找试卷列表成功");
				return LayUIResponse.success(paperListAlreadyScore);
			}
		}catch (Exception e){
			log.error("试卷列表中没有该试卷");
		}
		return LayUIResponse.fail();
	}

	/**
	 * 根据判断题和选择题的个数自动选题
	 * @param autoSelectQuestion
	 * @return
	 */
	@GetMapping("/autoSelectQuestion")
	public ServerResponse autoSelectQuestionByNum(AutoSelectQuestion autoSelectQuestion){
		log.info("根据题目总个数和难易程度自动选题");
		List<PaperDetailAndQuestion> autoSelectQuestoinList = null;
		try{
			//自动选题
			autoSelectQuestoinList = paperService.autoSelectQuestionByNum(autoSelectQuestion);
			if(autoSelectQuestoinList != null && autoSelectQuestoinList.size()>0){
				log.info("根据题目总个数和难易程度自动选题成功");
				return ServerResponse.createBySuccess("自动选题成功",autoSelectQuestoinList);
			}

		}catch(Exception e){
			e.printStackTrace();
			log.error("根据题目总个数和难易程度自动选题失败");
		}
		return ServerResponse.createByErrorMessage("自动选题失败");
	}

	@GetMapping("/isPaper")
	public ServerResponse isPaper(Paper paper){
		log.info("判断是否有该试卷");
		List<Paper> paperList = null;
		try{
			paperList = paperService.queryPaperList(paper);
			if(paperList != null && paperList.size()!=0){
				log.info("判断成功");
				return ServerResponse.createBySuccess("试卷名称不能重复","false");
			}
		}catch (Exception e){
			log.error("判断失败");
		}
		return ServerResponse.createBySuccess();
	}
}
