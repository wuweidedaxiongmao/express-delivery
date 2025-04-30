package com.example.springboot.mapper;

import com.example.springboot.entity.ExpressType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface ExpressTypeMapper {
	List<ExpressType> selectAll(ExpressType expressType);

	//使用注解方式实现sql查询
	@Select("select * from express_type where id = #{id}")
	ExpressType selectById(Integer id);

	void insert(ExpressType expressType);

	void updateById(ExpressType expressType);

	@Delete("delete from express_type where id=#{id}")
	void deleteById(Integer id);

}
