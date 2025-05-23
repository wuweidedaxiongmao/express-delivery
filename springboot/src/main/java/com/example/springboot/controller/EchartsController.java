package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.entity.Orders;
import com.example.springboot.entity.Student;
import com.example.springboot.service.OrdersService;
import com.example.springboot.service.StudentService;
import com.example.springboot.value.OrderStatus;
import com.example.springboot.value.Role;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/echarts")
public class EchartsController {
	@Resource
	private OrdersService ordersService;
	@Resource
	private StudentService studentService;

	/**
	 * 不同订单状态的数量
	 * 返回饼图所需要的数据
	 * @return
	 */
	@GetMapping("/ordersStatus")
	public Result getOrdersStatusData() {
		List<String> orderStatusList=new ArrayList<>();
		orderStatusList.add(OrderStatus.WAIT);
		orderStatusList.add(OrderStatus.TRANSPORT);
		orderStatusList.add(OrderStatus.DELIVERY);
		orderStatusList.add(OrderStatus.SIGN);
		List<Map<String,Object>> mapsList=new ArrayList<>();

		for (String orderStatus : orderStatusList) {
			final List<Orders> ordersList = ordersService.selectAll(null);
			final Long count = ordersList.stream().filter(order -> orderStatus.equals(order.getStatus())).count();
			Map<String,Object> map=new HashMap<>();
			map.put("value",count);
			map.put("name",orderStatus);
			mapsList.add(map);
		}
		return Result.success(mapsList);
	}

	/**
	 * 返回学生与代取员的统计数量对比
	 * 返回柱状图所需要的数据
	 * @return
	 */
	@GetMapping("/studentRole")
	public Result getStudentSexData(){
		Map<String,Object> map=new HashMap<>();
		List<String> roleNameList=new ArrayList<>();
		roleNameList.add(Role.STUDENT);
		roleNameList.add(Role.COURIER);
		//map.put("name",roleNameList);
		List<Long> roleCountList=new ArrayList<>();
		final List<Student> students = studentService.selectAll(null);
		for (String role : roleNameList) {
			final long count = students.stream().filter(student -> role.equals(student.getRole())).count();
			roleCountList.add(count);
		}
		List<String> nameList = roleNameList.stream()
			.map(role -> {
				switch (role) {
					case "STU":
						return "学生";
					case "COUR":
						return "代取员";
					default:
						return role; // 保持其他未匹配的角色名不变
				}
			})
			.collect(Collectors.toList());
		map.put("name",nameList);
		map.put("count",roleCountList);
		return Result.success(map);
	}


}
