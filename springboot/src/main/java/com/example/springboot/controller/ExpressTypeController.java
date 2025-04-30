package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.entity.ExpressType;
import com.example.springboot.service.ExpressTypeService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expressType")
public class ExpressTypeController {

	@Resource
	private ExpressTypeService expressTypeService;

	@PostMapping("/add")
	public Result add(@RequestBody ExpressType expressType){
		expressTypeService.add(expressType);
		return Result.success();
	}

	@DeleteMapping("/deleteById")
	public Result deleteById(@RequestParam Integer id){
		expressTypeService.delete(id);
		return Result.success();
	}
	@DeleteMapping("/deleteBatch")
	public Result deleteBatch(@RequestBody List<Integer> ids){
		expressTypeService.deleteBatch(ids);
		return Result.success();
	}

	@PutMapping("/update")
	public Result update(@RequestBody ExpressType expressType){
		expressTypeService.update(expressType);
		return Result.success();
	}


	@GetMapping("/selectById/{id}")
	public Result selectById(@PathVariable Integer id){
		ExpressType expressType=expressTypeService.selectById(id);
		return Result.success(expressType);
	}

	@GetMapping("/selectAll")
	public Result selectAll(ExpressType expressType){
		//传参数通过typeName用来进行模糊查询
		//当typeName为null时候，进行的就是查询全部
		List<ExpressType> list=expressTypeService.selectAll(expressType);
		return Result.success(list);
	}

	//分页查询
	@GetMapping("/selectPage")
	public Result selectPage(ExpressType expressType,
	                         @RequestParam(defaultValue = "1") Integer pageNum,
	                         @RequestParam(defaultValue = "10") Integer pageSize){
		PageInfo<ExpressType> expressTypePageInfo = expressTypeService.selectPage(expressType,pageNum, pageSize);
		return Result.success(expressTypePageInfo);
	}

}
