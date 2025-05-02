package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.entity.Account;
import com.example.springboot.entity.Admin;
import com.example.springboot.entity.Student;
import com.example.springboot.exception.CustomException;
import com.example.springboot.service.AdminService;
import com.example.springboot.service.StudentService;
import com.example.springboot.value.Role;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {
	@Resource
	private StudentService studentService;

	@Resource
	private AdminService adminService;

	@PostMapping("/hello")
	public Result hello(){
		return Result.success("hello");
	}

	@PostMapping("/login")
	public Result login(@RequestBody Account account){
		Account login;
		if(Role.ADMIN.equals(account.getRole())){
			//管理员角色
			login=adminService.login(account);
			return Result.success((Admin)login);
		}else if(Role.STUDENT.equals(account.getRole())||Role.COURIER.equals(account.getRole())){
			//学生角色
			login = studentService.login(account);
			return Result.success((Student)login);
			//?? 代取员的账号，按理来说也能通过学生身份进入首页
		}else{
			throw new CustomException("500","/login接口，角色方面出现问题");
		}
	}

	@PostMapping("/register")
	public Result register(@RequestBody Student student){
		studentService.add(student);
		return Result.success();
	}


}
