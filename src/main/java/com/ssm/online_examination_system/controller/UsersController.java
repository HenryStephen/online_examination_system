package com.ssm.online_examination_system.controller;

import com.ssm.online_examination_system.annotation.StudentToken;
import com.ssm.online_examination_system.annotation.TeacherToken;
import com.ssm.online_examination_system.po.Users;
import com.ssm.online_examination_system.service.UsersService;
import com.ssm.online_examination_system.utils.LayUIResponse;
import com.ssm.online_examination_system.utils.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@TeacherToken
@StudentToken
@RequestMapping("/user")
public class UsersController {

	@Autowired
	private UsersService usersService;

	/**
	 * 查询用户信息
	 * @return
	 */
	@GetMapping("/queryInfoByUserName")
	public Users queryUserInfo(HttpServletRequest request){
		log.info("查看用户信息");
		Users users = null;
		try{
			String userName = request.getHeader("userName");//获取userName的值
			if(userName != null){
				users = new Users();
				users.setUserName(userName);
				users = usersService.queryUserInfo(users);
				log.info("查看用户信息成功");
				return users;
			}
		}catch (Exception e){
			e.printStackTrace();
			log.info("查看用户信息失败");
		}
		return users;

	}

	/**
	 *  修改用户密码
	 * @param users
	 * @param request
	 * @return
	 */
	@PostMapping("/modifyPassword")
	public LayUIResponse modifyUserPassword(Users users, HttpServletRequest request){
		log.info("修改用户密码");
		try{
			String userName = request.getHeader("userName");//获取userName的值
			Users usersTemp = new Users();
			usersTemp.setUserName(userName);
			usersTemp = usersService.queryUserInfo(usersTemp);//根据用户名查询的user
			users.setUserId(usersTemp.getUserId());//设置当前user的ID
			boolean flag = usersService.modifyUserPassword(users);
			if(flag){
				log.info("修改用户密码成功");
				return LayUIResponse.success();
			}
		}catch (Exception e){
			log.error("修改用户密码失败");
		}
		return LayUIResponse.fail();
	}

	/**
	 * 修改用户信息
	 * @param users
	 * @param request
	 * @return
	 */
	@GetMapping("/modifyInfo")
	public LayUIResponse modifyUserInfo(Users users, HttpServletRequest request){
		log.info("修改用户信息");
		try{
			String userName = request.getHeader("userName");
			Users usersTemp = new Users();
			usersTemp.setUserName(userName);
			usersTemp = usersService.queryUserInfo(usersTemp);
			boolean flag = usersService.modifyUserInfo(users,usersTemp);
			if(flag){
				log.info("修改用户信息成功");
				return LayUIResponse.success();
			}

		}catch (Exception e){
			e.printStackTrace();
			log.error("修改管理员信息失败");
		}
		return LayUIResponse.fail();
	}

	/**
	 * 查看用户信息
	 * @param users
	 * @return
	 */
	@GetMapping("/queryInfo")
	public Users queryUserInfo(Users users){
		log.info("查看用户信息");
		Users usersTemp = null;
		try{
			usersTemp = usersService.queryUserInfo(users);
			log.info("查看用户信息成功");
			return usersTemp;
		}catch (Exception e){
			log.info("查看用户信息失败");
		}
		return usersTemp;
	}

	/**
	 * 判断旧密码是否正确
	 * @param users
	 * @return
	 */
	@PostMapping("/queryUsersByPassword")
	public ServerResponse queryUsersByPassword(Users users,HttpServletRequest request){
		log.info("查看用户信息");
		try{
			String userName = request.getHeader("userName");
			Users usersTemp = new Users();
			usersTemp.setUserName(userName);
			//根据用户name找到user
			usersTemp = usersService.queryUserInfo(usersTemp);
			usersTemp.setUserPassword(users.getUserPassword());
			Users users1 = usersService.queryUserInfo(usersTemp);
			if(users1 != null) {
				log.info("查看用户信息成功");
				return ServerResponse.createBySuccess("密码正确", "true");
			}
		}catch (Exception e){
			log.info("查看用户信息失败");
		}
		return ServerResponse.createByErrorMessage("原密码不正确");
	}

}
