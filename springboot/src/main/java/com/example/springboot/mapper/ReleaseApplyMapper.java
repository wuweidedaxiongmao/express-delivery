package com.example.springboot.mapper;

import com.example.springboot.entity.ReleaseApply;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReleaseApplyMapper {
	List<ReleaseApply> selectAll(ReleaseApply releaseApply);

	//使用注解方式实现sql查询
	@Select("select * from release_apply where id = #{id}")
	ReleaseApply selectById(Integer id);

	void insert(ReleaseApply releaseApply);

	void updateById(ReleaseApply releaseApply);

	@Delete("delete from release_apply where id=#{id}")
	void deleteById(Integer id);

	@Select("select * from release_apply where courier_id = #{courierId} and status='Pending'")
	ReleaseApply selectByCourierId(Integer courierId);
}
