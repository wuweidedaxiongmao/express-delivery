package com.example.springboot.mapper;

import com.example.springboot.entity.Information;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface InformationMapper {
	List<Information> selectAll(Information information);

	//使用注解方式实现sql查询
	@Select("select * from information where id = #{id}")
	Information selectById(Integer id);

	void insert(Information information);

	void updateById(Information information);

	@Delete("delete from information where id=#{id}")
	void deleteById(Integer id);

}
