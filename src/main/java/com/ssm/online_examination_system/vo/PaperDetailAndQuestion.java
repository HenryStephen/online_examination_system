package com.ssm.online_examination_system.vo;

import com.ssm.online_examination_system.po.Paperdetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaperDetailAndQuestion extends Paperdetail {

	/**
	 * 题目
	 */
	private String question;

	/**
	 * 正确答案【0、1、A、B、C、D】
	 */
	private String rightAnswer;

	/**
	 * 选项A
	 */
	private String optionA;

	/**
	 * 选项B
	 */
	private String optionB;

	/**
	 * 选项C
	 */
	private String optionC;

	/**
	 * 选项D
	 */
	private String optionD;

	/**
	 * 解析
	 */
	private String analysis;

	/**
	 * 分值
	 */
	private Integer grade;

	/**
	 * 题目类型【1为选择题，2为判断题】
	 */
	private Integer questionType;

}
