package com.ssm.online_examination_system.vo;

import com.ssm.online_examination_system.po.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionVo extends Question {

	/**
	 * 科目名称
	 */
	private String subjectName;

}
