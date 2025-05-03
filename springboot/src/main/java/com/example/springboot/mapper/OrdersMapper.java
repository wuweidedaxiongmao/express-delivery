package com.example.springboot.mapper;

import com.example.springboot.entity.Orders;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrdersMapper {
	List<Orders> selectAll(Orders orders);

	//使用注解方式实现sql查询
	@Select("select * from orders where id = #{id}")
	Orders selectById(Integer id);

	void insert(Orders orders);

	void updateById(Orders orders);

	@Delete("delete from orders where id=#{id}")
	void deleteById(Integer id);

}
