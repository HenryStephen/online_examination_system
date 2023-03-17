package com.ssm.online_examination_system.mapper.manual;

import com.ssm.online_examination_system.po.Admins;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminsDao {

	/**
	 * 根据选择查找管理员
	 * @param admins
	 * @return
	 */
	List<Admins> selectBySelective(Admins admins);

}
