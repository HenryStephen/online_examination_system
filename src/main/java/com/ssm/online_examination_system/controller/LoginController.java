package com.ssm.online_examination_system.controller;

import com.alibaba.fastjson.JSONObject;
import com.ssm.online_examination_system.annotation.StudentToken;
import com.ssm.online_examination_system.annotation.TeacherToken;
import com.ssm.online_examination_system.po.Admins;
import com.ssm.online_examination_system.po.Users;
import com.ssm.online_examination_system.service.AdminsService;
import com.ssm.online_examination_system.service.UsersService;
import com.ssm.online_examination_system.utils.ConstantKit;
import com.ssm.online_examination_system.utils.LayUIResponse;
import com.ssm.online_examination_system.utils.Md5TokenGenerator;
import com.ssm.online_examination_system.utils.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private AdminsService adminsService;
	@Autowired
	private UsersService usersService;
	@Autowired
	private Md5TokenGenerator tokenGenerator;

	/**
	 * 管理员登录
	 * @param admins
	 * @return
	 */
	@PostMapping("/admin")
	public LayUIResponse adminLogin(Admins admins){
		log.info("管理员登录");
		try{
			Admins serviceAdmin = adminsService.isAdmin(admins);
			JSONObject result = new JSONObject();
			if(serviceAdmin != null){
				Jedis jedis = new Jedis("127.0.0.1", 6379);
				String token = tokenGenerator.generate(serviceAdmin.getAdminName(), serviceAdmin.getAdminPassword());
				//将token和name进行双向绑定
				jedis.set(serviceAdmin.getAdminName(), token);
				jedis.expire(serviceAdmin.getAdminName(), ConstantKit.TOKEN_EXPIRE_TIME);//设置key生存时间，当key过期时，它会被自动删除，时间是秒
				jedis.set(token, serviceAdmin.getAdminName());
				jedis.expire(token, ConstantKit.TOKEN_EXPIRE_TIME);//设置key生存时间，当key过期时，它会被自动删除，时间是秒
				//设置token的生成时间
				Long currentTime = System.currentTimeMillis();
				jedis.set(token + serviceAdmin.getAdminName(), currentTime.toString());
				//用完关闭
				jedis.close();
				result.put("adminToken", token);
				result.put("adminName",serviceAdmin.getAdminName());
				log.info("管理员登录成功");
				return LayUIResponse.loginSuccess(result);
			}
		}catch (Exception e){
			log.error("管理员登录失败");
		}
		return LayUIResponse.loginFail();
	}

	/**
	 * 用户登录
	 * @param users
	 * @return
	 */
	@PostMapping("/user")
	public LayUIResponse userLogin(Users users){
		log.info("用户登录");
		try{
			Users serviceUser = usersService.isUser(users);
			JSONObject result = new JSONObject();
			if(serviceUser != null){
				Jedis jedis = new Jedis("127.0.0.1", 6379);
				String token = tokenGenerator.generate(serviceUser.getUserName(), serviceUser.getUserPassword());
				//用户名和token双向绑定
				jedis.set(serviceUser.getUserName(), token);
				//设置key生存时间，当key过期时，它会被自动删除，时间是秒
				jedis.expire(serviceUser.getUserName(), ConstantKit.TOKEN_EXPIRE_TIME);
				jedis.set(token, serviceUser.getUserName());
				//设置key生存时间，当key过期时，它会被自动删除，时间是秒
				jedis.expire(token, ConstantKit.TOKEN_EXPIRE_TIME);
				Long currentTime = System.currentTimeMillis();
				jedis.set(token + serviceUser.getUserName(), currentTime.toString());
				//用完关闭
				jedis.close();
				result.put("userToken", token);
				result.put("userName",serviceUser.getUserName());
				log.info("用户登录成功");
				return LayUIResponse.loginSuccess(result);
			}
		}catch (Exception e){
			e.printStackTrace();
			log.error("用户登录失败");
		}
		return LayUIResponse.fail();
	}

	/**
	 * 注册用户
	 * @param users
	 * @return
	 */
	@PostMapping("/register")
	public LayUIResponse registerUser(Users users){
		log.info("注册用户");
		try{
			boolean flag = usersService.registerUser(users);
			if(flag){
				log.info("注册用户成功");
				return LayUIResponse.success();
			}
		}catch (Exception e){
			e.printStackTrace();
			log.error("注册用户失败");
		}
		return LayUIResponse.fail();
	}

	/**
	 * 前端页面请求接口
	 * @return
	 */
	@GetMapping("/isTeacherToken")
	@TeacherToken
	public LayUIResponse isTeacherToken(){
		return LayUIResponse.success();
	}

	/**
	 * 前端页面请求接口
	 * @return
	 */
	@GetMapping("/isStudentToken")
	@StudentToken
	public LayUIResponse isStudentToken(){
		return LayUIResponse.success();
	}

	/**
	 * 根据token找name
	 * @param request
	 * @return
	 */
	@GetMapping("/getStudentNameByToken")
	@StudentToken
	public ServerResponse getStudentNameByToken(HttpServletRequest request){
		String userToken = request.getHeader("studentToken");
		String userName = "";//新建name
		Jedis jedis = new Jedis("127.0.0.1", 6379);//打开redis
		userName = jedis.get(userToken);//根据token获得name
		return ServerResponse.createBySuccess(userName);
	}

	/**
	 * 根据token找name
	 * @param request
	 * @return
	 */
	@GetMapping("/getTeacherNameByToken")
	@TeacherToken
	public ServerResponse getTeacherNameByToken(HttpServletRequest request){
		String userToken = request.getHeader("teacherToken");
		String userName = "";//新建name
		Jedis jedis = new Jedis("127.0.0.1", 6379);//打开redis
		userName = jedis.get(userToken);//根据token获得name
		System.out.println(userName);
		return ServerResponse.createBySuccess(userName);
	}

	/**
	 * 判断是否存在该学生
	 * @param users
	 * @param request
	 * @return
	 */
	@GetMapping("/hasStudent")
	@StudentToken
	public ServerResponse hasStudent(Users users, HttpServletRequest request){
		log.info("查看用户信息");
		try{
			String userName = request.getHeader("studentName");
			Users users1 = usersService.queryUserInfo(users);
			if(users1 != null){
				if(!users1.getUserName().equals(userName)){
					return ServerResponse.createBySuccess("学生名称不能重复","false");
				}
			}
		}catch (Exception e){
			log.info("查看管理员信息失败");
		}
		return ServerResponse.createBySuccess();
	}

	/**
	 * 判断是否存在该学生
	 * @param users
	 * @param request
	 * @return
	 */
	@GetMapping("/hasTeacher")
	@TeacherToken
	public ServerResponse hasTeacher(Users users, HttpServletRequest request){
		log.info("查看用户信息");
		try{
			String userName = request.getHeader("teacherName");
			Users users1 = usersService.queryUserInfo(users);
			if(users1 != null){
				if(!users1.getUserName().equals(userName)){
					return ServerResponse.createBySuccess("老师名称不能重复","false");
				}
			}
		}catch (Exception e){
			log.info("查看管理员信息失败");
		}
		return ServerResponse.createBySuccess();
	}
}
