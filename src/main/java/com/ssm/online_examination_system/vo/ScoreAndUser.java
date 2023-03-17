package com.ssm.online_examination_system.vo;

import com.ssm.online_examination_system.po.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreAndUser extends Users {
	/**
	 * 试卷Id
	 */
	private Integer paperId;
	/**
	 * 成绩
	 */
	private Integer score;
}
