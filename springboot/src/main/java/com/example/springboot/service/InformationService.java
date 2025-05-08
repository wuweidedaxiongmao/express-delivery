package com.example.springboot.service;

import com.example.springboot.entity.Information;
import com.example.springboot.mapper.InformationMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InformationService {

	@Resource
	private InformationMapper informationMapper;

	public void add(Information information) {
		information.setCreatedAt(LocalDateTime.now());
		information.setUpdatedAt(LocalDateTime.now());
		informationMapper.insert(information);
	}



	public void delete(Integer id) {
		informationMapper.deleteById(id);
	}

	public void deleteBatch(List<Integer> ids) {
		for (Integer id : ids) {
			informationMapper.deleteById(id);
		}
	}

	public void update(Information information) {
		information.setUpdatedAt(LocalDateTime.now());
		informationMapper.updateById(information);
	}
	public Information selectById(Integer id) {
		return informationMapper.selectById(id);
	}

	public List<Information> selectAll(Information information) {
		List<Information> list=informationMapper.selectAll(information);
		return list;
	}

	public PageInfo<Information> selectPage(Information information, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		List<Information> list = informationMapper.selectAll(information);
		//会在selectAll使用的sql语句中自动的添加关于分页相关的比如limit参数
		return PageInfo.of(list);
	}

}
