package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.entity.Feedback;
import com.example.springboot.entity.Identification;
import com.example.springboot.entity.ReleaseApply;
import com.example.springboot.entity.UpgradeApply;
import com.example.springboot.service.*;
import com.example.springboot.value.FeedbackStatus;
import com.example.springboot.value.ReviewStatus;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/homeData")
public class HomeDataController {
	@Resource
	private OrdersService ordersService;
	@Resource
	private IdentificationService identificationService;
	@Resource
	private UpgradeApplyService upgradeApplyService;
	@Resource
	private ReleaseApplyService releaseApplyService;
	@Resource
	private FeedbackService feedbackService;

	/**
	 * 后端接口，返回订单总数
	 *
	 * @return 封装订单总数数据，返回Result类
	 */
	@GetMapping("/orders")
	public Result getOrders(){
		return Result.success(ordersService.selectAllCount());
	}

	@GetMapping("/identifications")
	public Result getIdentifications(){
		final List<Identification> identifications = identificationService.selectAll(null);
		final long count = identifications.stream().filter(item -> item.getStatus().equals(ReviewStatus.PENDING)).count();
		return Result.success(count);
	}

	@GetMapping("/upgradeApplys")
	public Result getUpgradeApplys(){
		final List<UpgradeApply> upgradeApplies = upgradeApplyService.selectAll(null);
		final long count = upgradeApplies.stream().filter(item -> item.getStatus().equals(ReviewStatus.PENDING)).count();
		return Result.success(count);
	}

	@GetMapping("/releaseApplys")
	public Result getReleaseApplys(){
		final List<ReleaseApply> releaseApplies = releaseApplyService.selectAll(null);
		final long count = releaseApplies.stream().filter(r -> r.getStatus().equals(ReviewStatus.PENDING)).count();
		return Result.success(count);
	}

	@GetMapping("/feedbacks")
	public Result getFeedbacks(){
		final List<Feedback> feedbacks = feedbackService.selectAll(null);
		final long count = feedbacks.stream().filter(f -> f.getStatus().equals(FeedbackStatus.wait)).count();
		return Result.success(count);
	}

	@GetMapping
	public Result getAllData(){
		Map<String,Object> map=new HashMap<>();
		map.put("orders",getOrders().getData());
		map.put("identifications",getIdentifications().getData());
		map.put("upgradeApplys",getUpgradeApplys().getData());
		map.put("releaseApplys",getReleaseApplys().getData());
		map.put("feedbacks",getFeedbacks().getData());
		return Result.success(map);
	}

}
