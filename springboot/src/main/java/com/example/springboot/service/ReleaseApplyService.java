package com.example.springboot.service;

import com.example.springboot.entity.ReleaseApply;
import com.example.springboot.mapper.ReleaseApplyMapper;
import com.example.springboot.value.ReviewStatus;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReleaseApplyService {

	@Resource
	private ReleaseApplyMapper releaseApplyMapper;

	public void add(ReleaseApply releaseApply) {
		releaseApply.setStatus(ReviewStatus.PENDING); //设置状态
		releaseApply.setCreatedAt(LocalDateTime.now()); //设置提交时间
		releaseApplyMapper.insert(releaseApply);
	}



	public void delete(Integer id) {
		releaseApplyMapper.deleteById(id);
	}

	public void deleteBatch(List<Integer> ids) {
		for (Integer id : ids) {
			releaseApplyMapper.deleteById(id);
		}
	}

	public void update(ReleaseApply releaseApply) {
		releaseApply.setUpdatedAt(LocalDateTime.now()); //设置审核时间
		releaseApplyMapper.updateById(releaseApply);
	}
	public ReleaseApply selectById(Integer id) {
		return releaseApplyMapper.selectById(id);
	}

	public List<ReleaseApply> selectAll(ReleaseApply releaseApply) {
		List<ReleaseApply> list=releaseApplyMapper.selectAll(releaseApply);
		return list;
	}

	public PageInfo<ReleaseApply> selectPage(ReleaseApply releaseApply, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		List<ReleaseApply> list = releaseApplyMapper.selectAll(releaseApply);
		//会在selectAll使用的sql语句中自动的添加关于分页相关的比如limit参数
		return PageInfo.of(list);
	}

	public ReleaseApply selectByCourierId(Integer courierId) {
		return releaseApplyMapper.selectByCourierId(courierId);
	}
}
