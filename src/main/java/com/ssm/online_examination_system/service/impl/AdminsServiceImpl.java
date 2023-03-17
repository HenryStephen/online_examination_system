package com.ssm.online_examination_system.service.impl;

import com.ssm.online_examination_system.mapper.auto.AdminsMapper;
import com.ssm.online_examination_system.mapper.manual.AdminsDao;
import com.ssm.online_examination_system.po.Admins;
import com.ssm.online_examination_system.service.AdminsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminsServiceImpl implements AdminsService {

	@Autowired
	private AdminsDao adminsDao;
	@Autowired
	private AdminsMapper adminsMapper;

	/**
	 * 判断是否是管理员
	 * @param admins
	 * @return
	 */
	@Override
	public Admins isAdmin(Admins admins) {
		List<Admins> adminsList = adminsDao.selectBySelective(admins);
		if(adminsList!=null && adminsList.size()!=0){
			return adminsList.get(0);
		}
		return null;
	}

	/**
	 * 修改管理员密码
	 * @param admins
	 * @return
	 */
	@Override
	public boolean modifyAdminPassword(Admins admins) {
		int count = adminsMapper.updateByPrimaryKeySelective(admins);
		if(count>0){
			return true;
		}
		return false;
	}

	/**
	 * 修改管理员信息
	 * @param admins
	 * @return
	 */
	@Override
	public boolean modifyAdminInfo(Admins admins,Admins sessionAdmins) {
		//如果用户修改了用户名
		if(!admins.getAdminName().equals(sessionAdmins.getAdminName())){
			//首先查找是否存在该用户
			Admins adminsCopy = new Admins();
			adminsCopy.setAdminName(admins.getAdminName());
			List<Admins> adminsList = adminsDao.selectBySelective(adminsCopy);
			if(adminsList!=null)
				return false;
		}
		int count = adminsMapper.updateByPrimaryKeySelective(admins);
		if(count>0){
			return true;
		}
		return false;
	}

	/**
	 * 根据选择查看管理员信息
	 * @param admins
	 * @return
	 */
	@Override
	public Admins queryAdminInfo(Admins admins) {
		List<Admins> adminsList = adminsDao.selectBySelective(admins);
		if(adminsList != null){
			return adminsList.get(0);
		}
		return null;
	}
}
