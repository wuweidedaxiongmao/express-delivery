package com.example.springboot.mapper;

import com.example.springboot.entity.Admin;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdminMapper {
	List<Admin> selectAll(Admin admin);

	//使用注解方式实现sql查询
	@Select("select * from admin where id = #{id}")
	Admin selectById(Integer id);

	void insert(Admin admin);

	void updateById(Admin admin);

	@Delete("delete from admin where id=#{id}")
	void deleteById(Integer id);

	@Select("select * from admin where username=#{username}")
	Admin selectByUsername(String username);

}
