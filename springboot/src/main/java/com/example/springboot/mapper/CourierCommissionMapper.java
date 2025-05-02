package com.example.springboot.mapper;

import com.example.springboot.entity.CourierCommission;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CourierCommissionMapper {
	List<CourierCommission> selectAll(CourierCommission courierCommission);

	//使用注解方式实现sql查询
	@Select("select * from courier_commission where id = #{id}")
	CourierCommission selectById(Integer id);

	void insert(CourierCommission courierCommission);

	void updateById(CourierCommission courierCommission);

	@Delete("delete from courier_commission where id=#{id}")
	void deleteById(Integer id);

}
