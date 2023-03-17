package com.ssm.online_examination_system.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 该类为自动选题的一些基本信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutoSelectQuestion {

	/**
	 * 科目Id
	 */
	private int subjectId;

	/**
	 * 选择题数量
	 */
	private int selectionNum;

	/**
	 * 判断题数量
	 */
	private int judgementNum;

	/**
	 * 判断题难易程度
	 */
	private int judgementComplexity;

	/**
	 * 选择题难易程度
	 */
	private int selectionComplexity;

}
