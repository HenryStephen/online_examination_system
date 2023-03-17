package com.ssm.online_examination_system.service;

import com.ssm.online_examination_system.po.Users;

public interface UsersService {

	/**
	 * 判断是否用户
	 * @param users
	 * @return
	 */
	Users isUser(Users users);

	/**
	 * 修改用户信息
	 * @param users
	 * @return
	 */
	boolean modifyUserInfo(Users users,Users sessionUsers);

	/**
	 * 修改用户密码
	 * @param users
	 * @return
	 */
	boolean modifyUserPassword(Users users);

	/**
	 * 注册用户
	 * @param users
	 * @return
	 */
	boolean registerUser(Users users);

	/**
	 * 根据选择查看用户信息
	 * @param users
	 * @return
	 */
	Users queryUserInfo(Users users);

}
