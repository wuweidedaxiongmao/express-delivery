package com.example.springboot.service;

import com.example.springboot.entity.Address;
import com.example.springboot.mapper.AddressMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AddressService {

	@Resource
	private AddressMapper addressMapper;

	public void add(Address address) {
		LocalDateTime now = LocalDateTime.now();
		address.setCreatedAt(now);
		address.setUpdatedAt(now);
		addressMapper.insert(address);
	}



	public void delete(Integer id) {
		addressMapper.deleteById(id);
	}

	public void deleteBatch(List<Integer> ids) {
		for (Integer id : ids) {
			addressMapper.deleteById(id);
		}
	}

	public void update(Address address) {
		address.setUpdatedAt(LocalDateTime.now());
		addressMapper.updateById(address);
	}
	public Address selectById(Integer id) {
		return addressMapper.selectById(id);
	}

	public List<Address> selectAll(Address address) {
		List<Address> list=addressMapper.selectAll(address);
		return list;
	}

	public PageInfo<Address> selectPage(Address address, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		List<Address> list = addressMapper.selectAll(address);
		//会在selectAll使用的sql语句中自动的添加关于分页相关的比如limit参数
		return PageInfo.of(list);
	}

}
