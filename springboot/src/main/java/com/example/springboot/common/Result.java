package com.example.springboot.common;

import com.example.springboot.entity.ExpressType;
import com.example.springboot.exception.CustomException;

/**
 * 统一返回的包装类
 * 统一后端返回的类型
 */
public class Result {
	private String code;
	private String msg;
	private Object data;

	public static Result success(){
		Result result = new Result();
		result.setCode("200");
		result.setMsg("请求成功");
		return result;
	}
//	public static Result success(String message){
//		Result result = new Result();
//		result.setCode("200");
//		result.setMsg(message);
//		return result;
//	}

	public static Result success(Object obj){
		Result result=Result.success();
		result.setData(obj);
		return result;
	}

	public static Result error(){
		Result result=new Result();
		result.setCode("500");
		result.setMsg("系统错误");
		return result;
	}

	public static Result error(Object obj) {
		Result result = Result.error();

		if (obj instanceof CustomException) {
			CustomException ex = (CustomException) obj;
			result.setCode(ex.getCode());
			result.setMsg(ex.getMsg());
			System.out.println("code: " + ex.getCode());
		} else {
			result.setCode("500"); // 默认错误码
			result.setMsg("未知错误，请联系管理员");
			System.out.println((Exception)obj);
		}

		return result;
	}


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
