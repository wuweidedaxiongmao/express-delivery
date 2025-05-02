package com.example.springboot.mapper;

import com.example.springboot.entity.Identification;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface IdentificationMapper {
	List<Identification> selectAll(Identification identification);

	//使用注解方式实现sql查询
	@Select("select * from identification where id = #{id}")
	Identification selectById(Integer id);

	void insert(Identification identification);

	void updateById(Identification identification);

	@Delete("delete from identification where id=#{id}")
	void deleteById(Integer id);

	@Select("select * from identification where student_id=#{stuId}")
	Identification selectByStuId(Integer stuId);
}
