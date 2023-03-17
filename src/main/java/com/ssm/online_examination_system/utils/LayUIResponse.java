package com.ssm.online_examination_system.utils;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
/**
*@author zhanghonglin
*/
public  class LayUIResponse {

	/**
	 * 返回码
	 */
	private Integer code;

	/**
	 * 返回信息
	 */
	private String msg;

	/**
	 * 返回状态
	 */
	private Integer status;

	/**
	 * 数据条数
	 */
	private Long count;

	/**
	 * 分页列表数据集合
	 */
	private PageInfo<?> pageData;

	/**
	 * 列表数据集合
	 */
	private List<?> data;

	/**
	 * 认证信息
	 */
	private Object result;

	/**
	 * 登录成功函数
	 * @param result
	 * @return
	 */
	public static LayUIResponse loginSuccess(JSONObject result){
		return LayUIResponse.builder()
				.code(200)
				.msg(ResponseCode.LOGIN_SUCCESS.description)
				.status(ResponseCode.LOGIN_SUCCESS.code)
				.result(result)
				.build();

	}

	/**
	 * 登录失败函数
	 * @return
	 */
	public static LayUIResponse loginFail(){
		return LayUIResponse.builder()
				.code(401)
				.msg(ResponseCode.LOGIN_FAIL.description)
				.status(ResponseCode.LOGIN_FAIL.code)
				.build();
	}

	/**
	 * 需要登录函数
	 * @return
	 */
	public static LayUIResponse needLogin(){
		return LayUIResponse.builder()
				.code(401)
				.msg(ResponseCode.NEED_LOGIN.description)
				.status(ResponseCode.NEED_LOGIN.code)
				.build();
	}

	/**
	 * 返回数据成功函数（PageInfo）
	 * @param pageInfoList
	 * @return
	 */
	public static LayUIResponse success(PageInfo<?> pageInfoList) {
		return LayUIResponse.builder()
				.code(200)
				.msg(ResponseCode.SUCCESS.description)
				.status(ResponseCode.SUCCESS.code)
				.count(pageInfoList.getTotal())
				.data(pageInfoList.getList())
				.build();
	}

	/**
	 * 返回数据成功函数（List）
	 * @param responseList
	 * @return
	 */
	public static LayUIResponse success(List<?> responseList){
		return LayUIResponse.builder()
				.code(200)
				.msg(ResponseCode.SUCCESS.description)
				.status(ResponseCode.SUCCESS.code)
				.count(Long.valueOf(responseList.size()))
				.data(responseList)
				.build();
	}

	/**
	 * 返回信息成功函数（空）
	 * @return
	 */
	public static LayUIResponse success(){
		return LayUIResponse.builder()
				.code(200)
				.msg("无数据")
				.status(ResponseCode.SUCCESS.code)
				.count(Long.valueOf(0))
				.data(null)
				.build();
	}

	/**
	 * 返回信息失败函数
	 * @return
	 */
	public static LayUIResponse fail(){
		return LayUIResponse.builder()
				.code(500)
				.msg(ResponseCode.FAIL.description)
				.status(ResponseCode.FAIL.code)
				.build();
	}

	/**
	 * 转换成JsonObject
	 * @param layUIResponse
	 * @return
	 */
	public static JSONObject toJSONObject(LayUIResponse layUIResponse){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code",layUIResponse.getCode());
		jsonObject.put("msg",layUIResponse.getMsg());
		jsonObject.put("status",layUIResponse.getStatus());
		jsonObject.put("count",layUIResponse.getCount());
		jsonObject.put("data",layUIResponse.getData());
		jsonObject.put("result",layUIResponse.getResult());
		return jsonObject;
	}
}
