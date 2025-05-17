package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.entity.Identification;
import com.example.springboot.entity.UpgradeApply;
import com.example.springboot.service.UpgradeApplyService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/upgradeApply")
public class UpgradeApplyController {

	@Resource
	private UpgradeApplyService upgradeApplyService;

	@PostMapping("/add")
	public Result add(@RequestBody UpgradeApply upgradeApply){
		upgradeApplyService.add(upgradeApply);
		return Result.success();
	}

	@DeleteMapping("/deleteById")
	public Result deleteById(@RequestParam Integer id){
		upgradeApplyService.delete(id);
		return Result.success();
	}
	@DeleteMapping("/deleteBatch")
	public Result deleteBatch(@RequestBody List<Integer> ids){
		upgradeApplyService.deleteBatch(ids);
		return Result.success();
	}

	@PutMapping("/update")
	public Result update(@RequestBody UpgradeApply upgradeApply){
		upgradeApplyService.update(upgradeApply);
		return Result.success();
	}


	@GetMapping("/selectById/{id}")
	public Result selectById(@PathVariable Integer id){
		UpgradeApply upgradeApply=upgradeApplyService.selectById(id);
		return Result.success(upgradeApply);
	}

	@GetMapping("/selectByCourierId/{CourierId}")
	public Result selectByCourierId(@PathVariable Integer CourierId){
		//根据CourierId查询数据，并且保证申请表的状态为“Pending”，所有只会返回一条数据
		UpgradeApply upgradeApply=upgradeApplyService.selectByCourierId(CourierId);
		return Result.success(upgradeApply);
	}

	@GetMapping("/selectAll")
	public Result selectAll(UpgradeApply upgradeApply){
		//传参数通过id用来进行模糊查询
		//当id为null时候，进行的就是查询全部
		List<UpgradeApply> list=upgradeApplyService.selectAll(upgradeApply);
		return Result.success(list);
	}

	//分页查询
	@GetMapping("/selectPage")
	public Result selectPage(UpgradeApply upgradeApply,
	                         @RequestParam(defaultValue = "1") Integer pageNum,
	                         @RequestParam(defaultValue = "10") Integer pageSize){
		PageInfo<UpgradeApply> upgradeApplyPageInfo = upgradeApplyService.selectPage(upgradeApply,pageNum, pageSize);
		return Result.success(upgradeApplyPageInfo);
	}

}
