package com.example.springboot.service;

import com.example.springboot.entity.CourierCommission;
import com.example.springboot.mapper.CourierCommissionMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourierCommissionService {

	@Resource
	private CourierCommissionMapper courierCommissionMapper;

	public void add(CourierCommission courierCommission) {
		courierCommissionMapper.insert(courierCommission);
	}



	public void delete(Integer id) {
		courierCommissionMapper.deleteById(id);
	}

	public void deleteBatch(List<Integer> ids) {
		for (Integer id : ids) {
			courierCommissionMapper.deleteById(id);
		}
	}

	public void update(CourierCommission courierCommission) {
		courierCommissionMapper.updateById(courierCommission);
	}
	public CourierCommission selectById(Integer id) {
		return courierCommissionMapper.selectById(id);
	}

	public List<CourierCommission> selectAll(CourierCommission courierCommission) {
		List<CourierCommission> list=courierCommissionMapper.selectAll(courierCommission);
		return list;
	}

	public PageInfo<CourierCommission> selectPage(CourierCommission courierCommission, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		List<CourierCommission> list = courierCommissionMapper.selectAll(courierCommission);
		//会在selectAll使用的sql语句中自动的添加关于分页相关的比如limit参数
		return PageInfo.of(list);
	}

}
