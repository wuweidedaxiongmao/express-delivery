package com.example.springboot.service;

import com.example.springboot.entity.Student;
import com.example.springboot.mapper.StudentMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

	@Resource
	private StudentMapper studentMapper;

	public void add(Student student) {
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

}
