package com.ssm.online_examination_system.mapper.manual;

import com.ssm.online_examination_system.po.Users;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersDao {

	/**
	 * 根据选择查找用户
	 * @param users
	 * @return
	 */
	List<Users> selectBySelective(Users users);

	/**
	 * 根据用户名查找用户
	 * @author luojiarui
	 */
	Users selectUserByUserName(Users users);
}
