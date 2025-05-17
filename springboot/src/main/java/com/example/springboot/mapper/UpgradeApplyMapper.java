package com.example.springboot.mapper;

import com.example.springboot.entity.UpgradeApply;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UpgradeApplyMapper {
	List<UpgradeApply> selectAll(UpgradeApply upgradeApply);

	//使用注解方式实现sql查询
	@Select("select * from upgrade_apply where id = #{id}")
	UpgradeApply selectById(Integer id);

	void insert(UpgradeApply upgradeApply);

	void updateById(UpgradeApply upgradeApply);

	@Delete("delete from upgrade_apply where id=#{id}")
	void deleteById(Integer id);

	@Select("select * from upgrade_apply where courier_id=#{courierId} AND status='Pending'")
	UpgradeApply selectByCourierId(Integer courierId);
}
