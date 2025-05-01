package com.example.springboot.mapper;

import com.example.springboot.entity.Student;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudentMapper {
	List<Student> selectAll(Student student);

	//使用注解方式实现sql查询
	@Select("select * from student where id = #{id}")
	Student selectById(Integer id);

	void insert(Student student);

	void updateById(Student student);

	@Delete("delete from student where id=#{id}")
	void deleteById(Integer id);

	@Select("select * from student where username=#{username}")
	Student selectByUsername(String username);

}
