package com.example.springboot.service;

import cn.hutool.core.util.StrUtil;
import com.example.springboot.entity.ExpressType;
import com.example.springboot.exception.CustomException;
import com.example.springboot.mapper.ExpressTypeMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpressTypeService {

	@Resource
	private ExpressTypeMapper expressTypeMapper;

	public void add(ExpressType expressType) {
		expressTypeMapper.insert(expressType);
	}



	public void delete(Integer id) {
		expressTypeMapper.deleteById(id);
	}

	public void deleteBatch(List<Integer> ids) {
		for (Integer id : ids) {
			expressTypeMapper.deleteById(id);
		}
	}

	public void update(ExpressType expressType) {
		expressTypeMapper.updateById(expressType);
	}
	public ExpressType selectById(Integer id) {
		return expressTypeMapper.selectById(id);
	}

	public List<ExpressType> selectAll(ExpressType expressType) {
		List<ExpressType> list=expressTypeMapper.selectAll(expressType);
		return list;
	}

	public PageInfo<ExpressType> selectPage(ExpressType expressType, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		List<ExpressType> list = expressTypeMapper.selectAll(expressType);
		//会在selectAll使用的sql语句中自动的添加关于分页相关的比如limit参数
		return PageInfo.of(list);
	}

}
