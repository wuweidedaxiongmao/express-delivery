package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.entity.Identification;
import com.example.springboot.service.IdentificationService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/identification")
public class IdentificationController {

	@Resource
	private IdentificationService identificationService;

	@PostMapping("/add")
	public Result add(@RequestBody Identification identification){
		identificationService.add(identification);
		return Result.success();
	}

	@DeleteMapping("/deleteById")
	public Result deleteById(@RequestParam Integer id){
		identificationService.delete(id);
		return Result.success();
	}
	@DeleteMapping("/deleteBatch")
	public Result deleteBatch(@RequestBody List<Integer> ids){
		identificationService.deleteBatch(ids);
		return Result.success();
	}

	@PutMapping("/update")
	public Result update(@RequestBody Identification identification){
		identificationService.update(identification);
		return Result.success();
	}


	@GetMapping("/selectById/{id}")
	public Result selectById(@PathVariable Integer id){
		Identification identification=identificationService.selectById(id);
		return Result.success(identification);
	}

	@GetMapping("/selectByStuId/{stuId}")
	public Result selectByStuId(@PathVariable Integer stuId){
		Identification identification=identificationService.selectByStuId(stuId);
		return Result.success(identification);
	}

	@GetMapping("/selectAll")
	public Result selectAll(Identification identification){
		//传参数通过name用来进行模糊查询
		//当name为null时候，进行的就是查询全部
		List<Identification> list=identificationService.selectAll(identification);
		return Result.success(list);
	}

	//分页查询
	@GetMapping("/selectPage")
	public Result selectPage(Identification identification,
	                         @RequestParam(defaultValue = "1") Integer pageNum,
	                         @RequestParam(defaultValue = "10") Integer pageSize){
		PageInfo<Identification> identificationPageInfo = identificationService.selectPage(identification,pageNum, pageSize);
		return Result.success(identificationPageInfo);
	}

}
