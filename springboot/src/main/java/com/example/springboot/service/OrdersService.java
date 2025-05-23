package com.example.springboot.service;

import com.example.springboot.entity.Orders;
import com.example.springboot.mapper.OrdersMapper;
import com.example.springboot.value.OrderStatus;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrdersService {

	@Resource
	private OrdersMapper ordersMapper;

	public void add(Orders orders) {
		orders.setCreateTime(LocalDateTime.now());
		orders.setStatus(OrderStatus.WAIT);
		ordersMapper.insert(orders);
	}



	public void delete(Integer id) {
		ordersMapper.deleteById(id);
	}

	public void deleteBatch(List<Integer> ids) {
		for (Integer id : ids) {
			ordersMapper.deleteById(id);
		}
	}

	public void update(Orders orders) {
		ordersMapper.updateById(orders);
	}
	public Orders selectById(Integer id) {
		return ordersMapper.selectById(id);
	}

	public List<Orders> selectAll(Orders orders) {
		List<Orders> list=ordersMapper.selectAll(orders);
		return list;
	}

	public PageInfo<Orders> selectPage(Orders orders, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		List<Orders> list = ordersMapper.selectAll(orders);
		//会在selectAll使用的sql语句中自动的添加关于分页相关的比如limit参数
		return PageInfo.of(list);
	}

	public Long selectAllCount(){
		return ordersMapper.selectAllCount();
	}

}
