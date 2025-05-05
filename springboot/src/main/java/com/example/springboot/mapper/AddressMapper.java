package com.example.springboot.mapper;

import com.example.springboot.entity.Address;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AddressMapper {
	List<Address> selectAll(Address address);

	//使用注解方式实现sql查询
	@Select("select * from address where id = #{id}")
	Address selectById(Integer id);

	void insert(Address address);

	void updateById(Address address);

	@Delete("delete from address where id=#{id}")
	void deleteById(Integer id);

}
