package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.entity.CourierCommission;
import com.example.springboot.service.CourierCommissionService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courierCommission")
public class CourierCommissionController {

	@Resource
	private CourierCommissionService courierCommissionService;

	@PostMapping("/add")
	public Result add(@RequestBody CourierCommission courierCommission){
		courierCommissionService.add(courierCommission);
		return Result.success();
	}

	@DeleteMapping("/deleteById")
	public Result deleteById(@RequestParam Integer id){
		courierCommissionService.delete(id);
		return Result.success();
	}
	@DeleteMapping("/deleteBatch")
	public Result deleteBatch(@RequestBody List<Integer> ids){
		courierCommissionService.deleteBatch(ids);
		return Result.success();
	}

	@PutMapping("/update")
	public Result update(@RequestBody CourierCommission courierCommission){
		courierCommissionService.update(courierCommission);
		return Result.success();
	}


	@GetMapping("/selectById/{id}")
	public Result selectById(@PathVariable Integer id){
		CourierCommission courierCommission=courierCommissionService.selectById(id);
		return Result.success(courierCommission);
	}

	@GetMapping("/selectAll")
	public Result selectAll(CourierCommission courierCommission){
		//传参数通过courierLevel用来进行模糊查询
		//当courierLevel为null时候，进行的就是查询全部
		List<CourierCommission> list=courierCommissionService.selectAll(courierCommission);
		return Result.success(list);
	}

	//分页查询
	@GetMapping("/selectPage")
	public Result selectPage(CourierCommission courierCommission,
	                         @RequestParam(defaultValue = "1") Integer pageNum,
	                         @RequestParam(defaultValue = "10") Integer pageSize){
		PageInfo<CourierCommission> courierCommissionPageInfo = courierCommissionService.selectPage(courierCommission,pageNum, pageSize);
		return Result.success(courierCommissionPageInfo);
	}

}
