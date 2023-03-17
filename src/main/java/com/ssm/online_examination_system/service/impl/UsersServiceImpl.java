package com.ssm.online_examination_system.service.impl;

import com.ssm.online_examination_system.mapper.auto.UsersMapper;
import com.ssm.online_examination_system.mapper.manual.UsersDao;
import com.ssm.online_examination_system.po.Users;
import com.ssm.online_examination_system.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {

	@Autowired
	private UsersDao usersDao;
	@Autowired
	private UsersMapper usersMapper;

	/**
	 * 判断是否用户
	 * @param users
	 * @return
	 */
	@Override
	public Users isUser(Users users) {
		List<Users> usersList = usersDao.selectBySelective(users);
		if(usersList!=null && usersList.size()!=0){
			return usersList.get(0);
		}
		return null;
	}

	/**
	 * 修改用户信息
	 * @param users
	 * @return
	 */
	@Override
	public boolean modifyUserInfo(Users users,Users sessionUsers) {
		if(!users.getUserName().equals(sessionUsers.getUserName())){
			//首先查找是否有该用户
			Users usersCopy = new Users();
			usersCopy.setUserName(users.getUserName());
			List<Users> usersList = usersDao.selectBySelective(usersCopy);
			if(usersList!=null)
				return false;
		}
		int count = usersMapper.updateByPrimaryKeySelective(users);
		if(count>0){
			return true;
		}
		return false;
	}

	/**
	 * 修改用户密码
	 * @param users
	 * @return
	 */
	@Override
	public boolean modifyUserPassword(Users users) {
		int count = usersMapper.updateByPrimaryKeySelective(users);
		if(count>0){
			return true;
		}
		return false;
	}

	/**
	 * 注册用户
	 * @param users
	 * @return
	 */
	@Override
	public boolean registerUser(Users users) {
		//首先查找是否有该用户
		Users usersCopy = new Users();
		usersCopy.setUserName(users.getUserName());
		List<Users> usersList = usersDao.selectBySelective(usersCopy);
		if(usersList == null || usersList.size() == 0){
			users.setUserStatus(2);
			int count = usersMapper.insertSelective(users);
			if(count>0){
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据选择查看用户信息
	 * @param users
	 * @return
	 */
	@Override
	public Users queryUserInfo(Users users) {
		List<Users> usersList = usersDao.selectBySelective(users);
		if(usersList != null){
			return usersList.get(0);
		}
		return null;
	}

}
