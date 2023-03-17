package com.ssm.online_examination_system.controller;

import com.ssm.online_examination_system.utils.LayUIResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequestMapping("/logout")
public class LogoutController {

	/**
	 * 学生退出
	 * @param request
	 * @return
	 */
	@GetMapping("/student")
	public LayUIResponse studentLogout(HttpServletRequest request){
		String studentToken = request.getHeader("studentToken");
		String studentName = "";//新建name
		Jedis jedis = new Jedis("127.0.0.1", 6379);//打开redis
		studentName = jedis.get(studentToken);//根据token获得name
		if(studentName != null){
			jedis.del(studentToken);
			jedis.del(studentName);
			jedis.del(studentToken+studentName);
			return LayUIResponse.success();
		}
		return LayUIResponse.fail();
	}

	/**
	 * 管理员退出
	 * @param request
	 * @return
	 */
	@GetMapping("/teacher")
	public LayUIResponse teacherLogout(HttpServletRequest request){
		String teacherToken = request.getHeader("teacherToken");
		String teacherName = "";//新建name
		Jedis jedis = new Jedis("127.0.0.1", 6379);//打开redis
		teacherName = jedis.get(teacherToken);//根据token获得name
		if(teacherName != null){
			jedis.del(teacherToken);
			jedis.del(teacherName);
			jedis.del(teacherToken+teacherName);
			return LayUIResponse.success();
		}
		return LayUIResponse.fail();
	}
}
