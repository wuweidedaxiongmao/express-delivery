package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.entity.Admin;
import com.example.springboot.service.AdminService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Resource
	private AdminService adminService;

	@PostMapping("/add")
	public Result add(@RequestBody Admin admin){
		adminService.add(admin);
		return Result.success();
	}

	@DeleteMapping("/deleteById")
	public Result deleteById(@RequestParam Integer id){
		adminService.delete(id);
		return Result.success();
	}
	@DeleteMapping("/deleteBatch")
	public Result deleteBatch(@RequestBody List<Integer> ids){
		adminService.deleteBatch(ids);
		return Result.success();
	}

	@PutMapping("/update")
	public Result update(@RequestBody Admin admin){
		adminService.update(admin);
		return Result.success();
	}
	

	@GetMapping("/selectById/{id}")
	public Result selectById(@PathVariable Integer id){
		Admin admin=adminService.selectById(id);
		return Result.success(admin);
	}

	@GetMapping("/selectAll")
	public Result selectAll(Admin admin){
		//传参数通过typeName用来进行模糊查询
		//当typeName为null时候，进行的就是查询全部
		List<Admin> list=adminService.selectAll(admin);
		return Result.success(list);
	}

	//分页查询
	@GetMapping("/selectPage")
	public Result selectPage(Admin admin,
	                         @RequestParam(defaultValue = "1") Integer pageNum,
	                         @RequestParam(defaultValue = "10") Integer pageSize){
		PageInfo<Admin> adminPageInfo = adminService.selectPage(admin,pageNum, pageSize);
		return Result.success(adminPageInfo);
	}

}
