package com.ssm.online_examination_system.controller;

import com.ssm.online_examination_system.annotation.AuthToken;
import com.ssm.online_examination_system.po.Admins;
import com.ssm.online_examination_system.service.AdminsService;
import com.ssm.online_examination_system.utils.LayUIResponse;
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
@RequestMapping("/admin")
@AuthToken
public class AdminsController {
	@Autowired
	private AdminsService adminsService;

	/**
	 * 查询管理员信息
	 * @return
	 */
	@GetMapping("/queryInfo")
	public Admins queryAdminInfo(HttpServletRequest request){
		log.info("查看管理员信息");
		Admins admins = null;
		try{
			String adminName = request.getHeader("adminName");//获取adminName的值
			if(adminName != null){
				admins = new Admins();
				admins.setAdminName(adminName);
				admins = adminsService.queryAdminInfo(admins);
				log.info("查看管理员信息成功");
				return admins;
			}
		}catch (Exception e){
			log.info("查看管理员信息失败");
		}
		return admins;
	}

	/**
	 * 修改管理员密码
	 * @param admins
	 * @return
	 */
	@PostMapping("/modifyPassword")
	public LayUIResponse modifyAdminPassword(Admins admins, HttpServletRequest request){
		log.info("修改管理员密码");
		try{
			String adminName = request.getHeader("adminName");//获取adminName的值
			Admins adminsTemp = new Admins();
			adminsTemp.setAdminName(adminName);
			adminsTemp = adminsService.queryAdminInfo(adminsTemp);//根据用户名查询的admin
			admins.setAdminId(adminsTemp.getAdminId());//设置当前admin的ID
			boolean flag = adminsService.modifyAdminPassword(admins);
			if(flag){
				log.info("修改管理员密码成功");
				return LayUIResponse.success();
			}
		}catch (Exception e){
			log.error("修改管理员密码失败");
		}
		return LayUIResponse.fail();
	}

	/**
	 * 修改管理员信息
	 * @param admins
	 * @param request
	 * @return
	 */
	@GetMapping("/modifyInfo")
	public LayUIResponse modifyAdminInfo(Admins admins, HttpServletRequest request){
		log.info("修改管理员信息");
		try{
			String adminName = request.getHeader("adminName");//获取adminName的值
			Admins adminsTemp = new Admins();
			adminsTemp.setAdminName(adminName);
			adminsTemp = adminsService.queryAdminInfo(adminsTemp);//根据用户名查询的admin
			boolean flag = adminsService.modifyAdminInfo(admins,adminsTemp);
			if(flag){
				log.info("修改管理员信息成功");
				return LayUIResponse.success();
			}
		}catch (Exception e){
			log.error("修改管理员信息失败");
		}
		return LayUIResponse.fail();
	}
	/**
	 * 判断旧密码是否正确
	 * @param admins
	 * @return
	 */
	@PostMapping("/queryAdminsByPassword")
	public ServerResponse queryAdminsByPassword(Admins admins, HttpServletRequest request){
		log.info("查看用户信息");
		try{
			String adminName = request.getHeader("adminName");
			Admins adminsTemp = new Admins();
			adminsTemp.setAdminName(adminName);
			//根据用户name找到user
			adminsTemp = adminsService.queryAdminInfo(adminsTemp);
			adminsTemp.setAdminPassword(admins.getAdminPassword());
			Admins admins1 = adminsService.queryAdminInfo(adminsTemp);
			if(admins1 != null) {
				log.info("查看用户信息成功");
				return ServerResponse.createBySuccess("密码正确", "true");
			}
		}catch (Exception e){
			log.info("查看用户信息失败");
		}
		return ServerResponse.createByErrorMessage("原密码不正确");
	}

	/**
	 * 前端页面请求接口
	 * @return
	 */
	@GetMapping("/isAdminToken")
	public LayUIResponse isAdminToken(){
		return LayUIResponse.success();
	}

	/**
	 * 根据token找name
	 * @param request
	 * @return
	 */
	@GetMapping("/getAdminNameByToken")
	public ServerResponse getAdminNameByToken(HttpServletRequest request){
		String adminToken = request.getHeader("adminToken");
		String adminName = "";//新建name
		Jedis jedis = new Jedis("127.0.0.1", 6379);//打开redis
		adminName = jedis.get(adminToken);//根据token获得name
		return ServerResponse.createBySuccess(adminName);
	}

	/**
	 * 管理员退出
	 * @param request
	 * @return
	 */
	@GetMapping("/logout")
	public LayUIResponse logout(HttpServletRequest request){
		String adminToken = request.getHeader("adminToken");
		String adminName = "";//新建name
		Jedis jedis = new Jedis("127.0.0.1", 6379);//打开redis
		adminName = jedis.get(adminToken);//根据token获得name
		if(adminName != null){
			jedis.del(adminToken);
			jedis.del(adminName);
			jedis.del(adminToken+adminName);
			return LayUIResponse.success();
		}
		return LayUIResponse.fail();
	}

	@GetMapping("/hasAdmin")
	public ServerResponse hasAdmin(Admins admins,HttpServletRequest request){
		log.info("查看管理员信息");
		try{
			String adminName = request.getHeader("adminName");
			Admins admins1 = adminsService.queryAdminInfo(admins);
			log.info("查看管理员信息成功");
			if(admins1 != null){
				if(!admins1.getAdminName().equals(adminName)){
					return ServerResponse.createBySuccess("管理员名称不能重复","false");
				}
			}
		}catch (Exception e){
			log.info("查看管理员信息失败");
		}
		return ServerResponse.createBySuccess();
	}
}
