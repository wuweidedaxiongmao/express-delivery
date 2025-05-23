package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.entity.ReleaseApply;
import com.example.springboot.service.ReleaseApplyService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/releaseApply")
public class ReleaseApplyController {

	@Resource
	private ReleaseApplyService releaseApplyService;

	@PostMapping("/add")
	public Result add(@RequestBody ReleaseApply releaseApply){
		releaseApplyService.add(releaseApply);
		return Result.success();
	}

	@DeleteMapping("/deleteById")
	public Result deleteById(@RequestParam Integer id){
		releaseApplyService.delete(id);
		return Result.success();
	}
	@DeleteMapping("/deleteBatch")
	public Result deleteBatch(@RequestBody List<Integer> ids){
		releaseApplyService.deleteBatch(ids);
		return Result.success();
	}

	@PutMapping("/update")
	public Result update(@RequestBody ReleaseApply releaseApply){
		releaseApplyService.update(releaseApply);
		return Result.success();
	}


	@GetMapping("/selectById/{id}")
	public Result selectById(@PathVariable Integer id){
		ReleaseApply releaseApply=releaseApplyService.selectById(id);
		return Result.success(releaseApply);
	}

	//通过courierId查询状态为Pending的申请表，只会出现一条数据
	@GetMapping("/selectByCourierId/{courierId}")
	public Result selectByCourierId(@PathVariable Integer courierId){
		ReleaseApply releaseApply=releaseApplyService.selectByCourierId(courierId);
		return Result.success(releaseApply);
	}

	@GetMapping("/selectAll")
	public Result selectAll(ReleaseApply releaseApply){
		//传参数通过status,name用来进行查询
		//当name和status为null时候，进行的就是查询全部
		List<ReleaseApply> list=releaseApplyService.selectAll(releaseApply);
		return Result.success(list);
	}

	//分页查询
	@GetMapping("/selectPage")
	public Result selectPage(ReleaseApply releaseApply,
	                         @RequestParam(defaultValue = "1") Integer pageNum,
	                         @RequestParam(defaultValue = "10") Integer pageSize){
		PageInfo<ReleaseApply> releaseApplyPageInfo = releaseApplyService.selectPage(releaseApply,pageNum, pageSize);
		return Result.success(releaseApplyPageInfo);
	}

}
