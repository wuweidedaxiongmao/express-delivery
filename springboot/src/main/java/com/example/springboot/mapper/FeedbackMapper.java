package com.example.springboot.mapper;

import com.example.springboot.entity.Feedback;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FeedbackMapper {
	List<Feedback> selectAll(Feedback feedback);

	//使用注解方式实现sql查询
	@Select("select * from feedback where id = #{id}")
	Feedback selectById(Integer id);

	void insert(Feedback feedback);

	void updateById(Feedback feedback);

	@Delete("delete from feedback where id=#{id}")
	void deleteById(Integer id);

}
