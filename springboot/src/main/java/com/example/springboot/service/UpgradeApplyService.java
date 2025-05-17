package com.example.springboot.service;

import com.example.springboot.entity.UpgradeApply;
import com.example.springboot.mapper.UpgradeApplyMapper;
import com.example.springboot.value.ReviewStatus;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UpgradeApplyService {

	@Resource
	private UpgradeApplyMapper upgradeApplyMapper;

	public void add(UpgradeApply upgradeApply) {
		upgradeApply.setStatus(ReviewStatus.PENDING);  //设置状态
		upgradeApply.setCreatedAt(LocalDateTime.now()); //设置申请时间
		upgradeApplyMapper.insert(upgradeApply);
	}



	public void delete(Integer id) {
		upgradeApplyMapper.deleteById(id);
	}

	public void deleteBatch(List<Integer> ids) {
		for (Integer id : ids) {
			upgradeApplyMapper.deleteById(id);
		}
	}

	public void update(UpgradeApply upgradeApply) {
		upgradeApply.setUpdatedAt(LocalDateTime.now());  //设置审核时间点
		upgradeApplyMapper.updateById(upgradeApply);
	}
	public UpgradeApply selectById(Integer id) {
		return upgradeApplyMapper.selectById(id);
	}

	public List<UpgradeApply> selectAll(UpgradeApply upgradeApply) {
		List<UpgradeApply> list=upgradeApplyMapper.selectAll(upgradeApply);
		return list;
	}

	public PageInfo<UpgradeApply> selectPage(UpgradeApply upgradeApply, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		List<UpgradeApply> list = upgradeApplyMapper.selectAll(upgradeApply);
		//会在selectAll使用的sql语句中自动的添加关于分页相关的比如limit参数
		return PageInfo.of(list);
	}

	public UpgradeApply selectByCourierId(Integer courierId) {
		return upgradeApplyMapper.selectByCourierId(courierId);
	}
}
