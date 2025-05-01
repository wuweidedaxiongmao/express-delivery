package com.example.springboot.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.springboot.entity.Account;
import com.example.springboot.entity.Admin;
import com.example.springboot.exception.CustomException;
import com.example.springboot.mapper.AdminMapper;
import com.example.springboot.value.Role;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminService {

	@Resource
	private AdminMapper adminMapper;

	public void add(Admin admin) {
		Admin dbAdmin = adminMapper.selectByUsername(admin.getUsername());
		if(ObjectUtil.isNotNull(dbAdmin)){
			throw new CustomException("500","用户已经存在，请重新输入用户名");
		}
		admin.setRole(Role.ADMIN);//设置role属性为ADMIN
		//设置createdAt
		LocalDateTime now =LocalDateTime.now();
		admin.setCreatedAt(now);
		admin.setUpdatedAt(now);
		adminMapper.insert(admin);
	}



	public void delete(Integer id) {
		adminMapper.deleteById(id);
	}

	public void deleteBatch(List<Integer> ids) {
		for (Integer id : ids) {
			adminMapper.deleteById(id);
		}
	}

	public void update(Admin admin) {
		admin.setUpdatedAt(LocalDateTime.now());
		adminMapper.updateById(admin);
	}

	public Admin selectById(Integer id) {
		return adminMapper.selectById(id);
	}

	public List<Admin> selectAll(Admin admin) {
		List<Admin> list=adminMapper.selectAll(admin);
		return list;
	}

	public PageInfo<Admin> selectPage(Admin admin, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		List<Admin> list = adminMapper.selectAll(admin);
		//会在selectAll使用的sql语句中自动的添加关于分页相关的比如limit参数
		return PageInfo.of(list);
	}

	public Admin login(Account account){
		Admin dbAdmin = adminMapper.selectByUsername(account.getUsername());
		if(dbAdmin==null){
			throw new CustomException("500","管理员用户不存在");
		}
		if(!dbAdmin.getPassword().equals(account.getPassword())){
			throw new CustomException("500","账号或者密码错误");
		}
		return dbAdmin;
	}

}
