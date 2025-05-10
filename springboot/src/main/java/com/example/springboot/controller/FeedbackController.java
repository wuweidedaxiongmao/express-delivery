package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.entity.Feedback;
import com.example.springboot.service.FeedbackService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

	@Resource
	private FeedbackService feedbackService;

	@PostMapping("/add")
	public Result add(@RequestBody Feedback feedback){
		feedbackService.add(feedback);
		return Result.success();
	}

	@DeleteMapping("/deleteById")
	public Result deleteById(@RequestParam Integer id){
		feedbackService.delete(id);
		return Result.success();
	}
	@DeleteMapping("/deleteBatch")
	public Result deleteBatch(@RequestBody List<Integer> ids){
		feedbackService.deleteBatch(ids);
		return Result.success();
	}

	@PutMapping("/update")
	public Result update(@RequestBody Feedback feedback){
		feedbackService.update(feedback);
		return Result.success();
	}


	@GetMapping("/selectById/{id}")
	public Result selectById(@PathVariable Integer id){
		Feedback feedback=feedbackService.selectById(id);
		return Result.success(feedback);
	}

	@GetMapping("/selectAll")
	public Result selectAll(Feedback feedback){
		//传参数通过typeName用来进行模糊查询
		//当typeName为null时候，进行的就是查询全部
		List<Feedback> list=feedbackService.selectAll(feedback);
		return Result.success(list);
	}

	//分页查询
	@GetMapping("/selectPage")
	public Result selectPage(Feedback feedback,
	                         @RequestParam(defaultValue = "1") Integer pageNum,
	                         @RequestParam(defaultValue = "10") Integer pageSize){
		PageInfo<Feedback> feedbackPageInfo = feedbackService.selectPage(feedback,pageNum, pageSize);
		return Result.success(feedbackPageInfo);
	}

}
