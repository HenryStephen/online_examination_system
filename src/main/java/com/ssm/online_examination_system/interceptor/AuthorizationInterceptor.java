package com.ssm.online_examination_system.interceptor;

import com.ssm.online_examination_system.annotation.AuthToken;
import com.ssm.online_examination_system.annotation.StudentToken;
import com.ssm.online_examination_system.annotation.TeacherToken;
import com.ssm.online_examination_system.utils.ConstantKit;
import com.ssm.online_examination_system.utils.LayUIResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * @author zhanghonglin
 * @Title: AuthorizationInterceptor
 * @date 2019/8/20
 */
@Slf4j
//默认名称是log
public class AuthorizationInterceptor implements HandlerInterceptor {

	//存放管理员认证信息的Header名称
	private String adminTokenName = "adminToken";
	//存放老师认证信息的Header名称
	private String teacherTokenName = "teacherToken";
	//存放学生认证信息的Header名称
	private String studentTokenName = "studentToken";
	//认证失败后返回的错误信息
	private String unauthorizedErrorMessage = "401 Unauthorized";
	//认证失败后返回的HTTP错误码
	private int unauthorizedErrorCode = HttpServletResponse.SC_UNAUTHORIZED;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		//代表认证状态
		boolean stateCode = false;
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();//获取处理器的方法
		// 如果方法或者类上有AuthToken注解
		if(method.getAnnotation(AuthToken.class) != null || handlerMethod.getBeanType().getAnnotation(AuthToken.class) != null
		|| method.getAnnotation(TeacherToken.class) != null || handlerMethod.getBeanType().getAnnotation(TeacherToken.class) != null
		|| method.getAnnotation(StudentToken.class) != null || handlerMethod.getBeanType().getAnnotation(StudentToken.class) != null){

			if (method.getAnnotation(AuthToken.class) != null || handlerMethod.getBeanType().getAnnotation(AuthToken.class) != null) {
				String adminToken = request.getHeader(adminTokenName);//获得管理员的token
				boolean flag = false;//默认认证成功
				if(adminToken != null && adminToken.length() != 0){
					flag = authorization(adminToken);//设置认证的成功与失败
				}
				if(flag)
					stateCode = true;
			}
			// 如果方法或者类上有TeacherToken注解
			if (method.getAnnotation(TeacherToken.class) != null || handlerMethod.getBeanType().getAnnotation(TeacherToken.class) != null) {
				String teacherToken = request.getHeader(teacherTokenName);//获得用户的token
				boolean flag = false;//默认认证成功
				if(teacherToken != null && teacherToken.length() != 0){
					flag = authorization(teacherToken);//设置认证的成功与失败
				}
				if(flag)
					stateCode = true;
			}
			// 如果方法或者类上有StudentToken注解
			if (method.getAnnotation(StudentToken.class) != null || handlerMethod.getBeanType().getAnnotation(StudentToken.class) != null) {
				String studentToken = request.getHeader(studentTokenName);//获得用户的token
				boolean flag = false;//默认认证成功
				if(studentToken != null && studentToken.length() != 0){
					flag = authorization(studentToken);//设置认证的成功与失败
				}
				if(flag)
					stateCode = true;
			}
			if(!stateCode){
				PrintWriter out = null;
				try {
					response.setStatus(200);//设置为成功
					response.setContentType(MediaType.APPLICATION_JSON_VALUE);//json格式
					out = response.getWriter();
					out.println(LayUIResponse.toJSONObject(LayUIResponse.needLogin()));//返回json对象
					return false;
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (null != out) {
						out.flush();
						out.close();
					}
				}
			}
			return true;
		}else{
			return true;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

	/**
	 * 用于管理员和用户认证的方法
	 * @param token
	 * @return
	 */
	public boolean authorization(String token){
		log.info("Get token from request is {} ", token);//记录token的值
		String name = "";//新建name
		Jedis jedis = new Jedis("127.0.0.1", 6379);//打开redis
		name = jedis.get(token);//根据token获得name
		log.info("Get name from Redis is {}", name);//记录name的值
		//如果name不为空
		if (name != null && !name.trim().equals("")) {
			Long tokenBirthTime = Long.valueOf(jedis.get(token + name));//获取token的生成时间
			log.info("token Birth time is: {}", tokenBirthTime);//输出token的生成时间
			Long diff = System.currentTimeMillis() - tokenBirthTime;//计算token已经存在的时间
			log.info("token is exist : {} ms", diff);//输出token已经存在的时间
			//如果token的时间过期，重新设置Redis中的token过期时间
			if (diff > ConstantKit.TOKEN_RESET_TIME) {
				//设置键的生存时间
				jedis.expire(name, ConstantKit.TOKEN_EXPIRE_TIME);
				jedis.expire(token, ConstantKit.TOKEN_EXPIRE_TIME);
				log.info("Reset expire time success!");
				Long newBirthTime = System.currentTimeMillis();
				//将新时间添加到redis中
				jedis.set(token + name, newBirthTime.toString());
			}
			jedis.close();//用完关闭
			return true;
		}
		return false;
	}
}
