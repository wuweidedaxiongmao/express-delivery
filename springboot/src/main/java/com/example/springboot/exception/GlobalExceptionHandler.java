package com.example.springboot.exception;

import com.example.springboot.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice("com.example.springboot.controller")
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	@ResponseBody  //返回json串
	public Result error(Exception e){
		return Result.error(e);
	}
}
