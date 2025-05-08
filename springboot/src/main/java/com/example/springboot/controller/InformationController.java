package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.entity.Information;
import com.example.springboot.service.InformationService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/information")
public class InformationController {

	@Resource
	private InformationService informationService;

	@PostMapping("/add")
	public Result add(@RequestBody Information information){
		informationService.add(information);
		return Result.success();
	}

	@DeleteMapping("/deleteById")
	public Result deleteById(@RequestParam Integer id){
		informationService.delete(id);
		return Result.success();
	}
	@DeleteMapping("/deleteBatch")
	public Result deleteBatch(@RequestBody List<Integer> ids){
		informationService.deleteBatch(ids);
		return Result.success();
	}

	@PutMapping("/update")
	public Result update(@RequestBody Information information){
		informationService.update(information);
		return Result.success();
	}


	@GetMapping("/selectById/{id}")
	public Result selectById(@PathVariable Integer id){
		Information information=informationService.selectById(id);
		return Result.success(information);
	}

	@GetMapping("/selectAll")
	public Result selectAll(Information information){
		//传参数通过title用来进行模糊查询
		//当title为null时候，进行的就是查询全部
		List<Information> list=informationService.selectAll(information);
		return Result.success(list);
	}

	//分页查询
	@GetMapping("/selectPage")
	public Result selectPage(Information information,
	                         @RequestParam(defaultValue = "1") Integer pageNum,
	                         @RequestParam(defaultValue = "10") Integer pageSize){
		PageInfo<Information> informationPageInfo = informationService.selectPage(information,pageNum, pageSize);
		return Result.success(informationPageInfo);
	}

}
