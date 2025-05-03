package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.entity.Orders;
import com.example.springboot.service.OrdersService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {

	@Resource
	private OrdersService ordersService;

	@PostMapping("/add")
	public Result add(@RequestBody Orders orders){
		ordersService.add(orders);
		return Result.success();
	}

	@DeleteMapping("/deleteById")
	public Result deleteById(@RequestParam Integer id){
		ordersService.delete(id);
		return Result.success();
	}
	@DeleteMapping("/deleteBatch")
	public Result deleteBatch(@RequestBody List<Integer> ids){
		ordersService.deleteBatch(ids);
		return Result.success();
	}

	@PutMapping("/update")
	public Result update(@RequestBody Orders orders){
		ordersService.update(orders);
		return Result.success();
	}


	@GetMapping("/selectById/{id}")
	public Result selectById(@PathVariable Integer id){
		Orders orders=ordersService.selectById(id);
		return Result.success(orders);
	}

	@GetMapping("/selectAll")
	public Result selectAll(Orders orders){
		//传参数通过id用来进行模糊查询
		//当id为null时候，进行的就是查询全部
		//用id直接当作订单号
		List<Orders> list=ordersService.selectAll(orders);
		return Result.success(list);
	}

	//分页查询
	@GetMapping("/selectPage")
	public Result selectPage(Orders orders,
	                         @RequestParam(defaultValue = "1") Integer pageNum,
	                         @RequestParam(defaultValue = "10") Integer pageSize){
		PageInfo<Orders> ordersPageInfo = ordersService.selectPage(orders,pageNum, pageSize);
		return Result.success(ordersPageInfo);
	}

}
