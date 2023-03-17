package com.ssm.online_examination_system;

import com.ssm.online_examination_system.mapper.auto.AdminsMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OnlineExaminationSystemApplicationTests {

	@Autowired
	private AdminsMapper adminsMapper;

	@Test
	public void testDeleteAdmin() {
		int count = adminsMapper.deleteByPrimaryKey(2);
		Assert.assertEquals(count,1);
	}

}
