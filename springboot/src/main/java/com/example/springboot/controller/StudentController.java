package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.entity.Student;
import com.example.springboot.service.StudentService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

	@Resource
	private StudentService studentService;

	@PostMapping("/add")
	public Result add(@RequestBody Student student){
		studentService.add(student);
		return Result.success();
	}

	@DeleteMapping("/deleteById")
	public Result deleteById(@RequestParam Integer id){
		studentService.delete(id);
		return Result.success();
	}
	@DeleteMapping("/deleteBatch")
	public Result deleteBatch(@RequestBody List<Integer> ids){
		studentService.deleteBatch(ids);
		return Result.success();
	}

	@PutMapping("/update")
	public Result update(@RequestBody Student student){
		studentService.update(student);
		return Result.success();
	}
	

	@GetMapping("/selectById/{id}")
	public Result selectById(@PathVariable Integer id){
		Student student=studentService.selectById(id);
		return Result.success(student);
	}

	@GetMapping("/selectAll")
	public Result selectAll(Student student){
		//传参数通过typeName用来进行模糊查询
		//当typeName为null时候，进行的就是查询全部
		List<Student> list=studentService.selectAll(student);
		return Result.success(list);
	}

	//分页查询
	@GetMapping("/selectPage")
	public Result selectPage(Student student,
	                         @RequestParam(defaultValue = "1") Integer pageNum,
	                         @RequestParam(defaultValue = "10") Integer pageSize){
		PageInfo<Student> studentPageInfo = studentService.selectPage(student,pageNum, pageSize);
		return Result.success(studentPageInfo);
	}

}
