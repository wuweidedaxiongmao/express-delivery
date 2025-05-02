package com.example.springboot.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.springboot.entity.Account;
import com.example.springboot.entity.Student;
import com.example.springboot.exception.CustomException;
import com.example.springboot.mapper.StudentMapper;
import com.example.springboot.value.Role;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentService {

	@Resource
	private StudentMapper studentMapper;

	public void add(Student student) {
		Student dbStudent = studentMapper.selectByUsername(student.getUsername());
		if(ObjectUtil.isNotNull(dbStudent)){
			throw new CustomException("500","用户已经存在，请重新输入用户名");
		}
		student.setRole(Role.STUDENT);//设置role属性为STUDETN
		//设置createdAt
		LocalDateTime now =LocalDateTime.now();
		student.setCreatedAt(now);
		student.setUpdatedAt(now);
		studentMapper.insert(student);
	}



	public void delete(Integer id) {
		studentMapper.deleteById(id);
	}

	public void deleteBatch(List<Integer> ids) {
		for (Integer id : ids) {
			studentMapper.deleteById(id);
		}
	}

	public void update(Student student) {
		student.setUpdatedAt(LocalDateTime.now());
		//id是唯一的，通过id进行更新
		studentMapper.updateById(student);
	}

	public Student selectById(Integer id) {
		return studentMapper.selectById(id);
	}

	public List<Student> selectAll(Student student) {
		List<Student> list=studentMapper.selectAll(student);
		return list;
	}

	public PageInfo<Student> selectPage(Student student, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		List<Student> list = studentMapper.selectAll(student);
		//会在selectAll使用的sql语句中自动的添加关于分页相关的比如limit参数
		return PageInfo.of(list);
	}

	public Student login(Account account){
		Student dbStudent = studentMapper.selectByUsername(account.getUsername());
		if(dbStudent==null){
			throw new CustomException("500","用户不存在");
		}
//		if(Role.COURIER.equals(dbStudent.getRole())){
//			throw new CustomException("500","you are courier,please choose the courier role!!!");
//		}
		if(!dbStudent.getPassword().equals(account.getPassword())){
			throw new CustomException("500","账号或者密码错误");
		}
		return dbStudent;
	}

}
