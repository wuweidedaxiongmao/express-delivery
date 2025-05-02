package com.example.springboot.service;

import com.example.springboot.entity.Identification;
import com.example.springboot.mapper.IdentificationMapper;
import com.example.springboot.value.ReviewStatus;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IdentificationService {

	@Resource
	private IdentificationMapper identificationMapper;

	public void add(Identification identification) {
		//设置createdAt
		LocalDateTime now =LocalDateTime.now();
		identification.setCreatedAt(now);
		identification.setUpdatedAt(now);
		//设置初始状态为待审核
		identification.setStatus(ReviewStatus.PENDING);
		identificationMapper.insert(identification);
	}



	public void delete(Integer id) {
		identificationMapper.deleteById(id);
	}

	public void deleteBatch(List<Integer> ids) {
		for (Integer id : ids) {
			identificationMapper.deleteById(id);
		}
	}

	public void update(Identification identification) {
		//管理员修改时间
		identification.setUpdatedAt(LocalDateTime.now());
		//即使修改也是审核中
		identification.setStatus(ReviewStatus.PENDING);
		identificationMapper.updateById(identification);
	}
	public Identification selectById(Integer id) {
		return identificationMapper.selectById(id);
	}

	public List<Identification> selectAll(Identification identification) {
		List<Identification> list=identificationMapper.selectAll(identification);
		return list;
	}

	public PageInfo<Identification> selectPage(Identification identification, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		List<Identification> list = identificationMapper.selectAll(identification);
		//会在selectAll使用的sql语句中自动的添加关于分页相关的比如limit参数
		return PageInfo.of(list);
	}

	public Identification selectByStuId(Integer stuId) {
		return identificationMapper.selectByStuId(stuId);
	}
}
