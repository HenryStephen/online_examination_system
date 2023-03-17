package com.ssm.online_examination_system.service;

import com.ssm.online_examination_system.po.Admins;

public interface AdminsService {

	/**
	 * 判断是否是管理员
	 * @param admins
	 * @return
	 */
	Admins isAdmin(Admins admins);

	/**
	 * 修改管理员密码
	 * @param admins
	 * @return
	 */
	boolean modifyAdminPassword(Admins admins);

	/**
	 * 修改管理员信息
	 * @param admins
	 * @return
	 */
	boolean modifyAdminInfo(Admins admins,Admins sessionAdmins);

	/**
	 * 根据选择查看管理员信息
	 * @param admins
	 * @return
	 */
	Admins queryAdminInfo(Admins admins);
}
