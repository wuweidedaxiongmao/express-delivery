package com.example.springboot.service;

import com.example.springboot.entity.Feedback;
import com.example.springboot.mapper.FeedbackMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FeedbackService {

	@Resource
	private FeedbackMapper feedbackMapper;

	public void add(Feedback feedback) {
		LocalDateTime now=LocalDateTime.now();
		feedback.setCreatedAt(now);
		feedback.setUpdatedAt(now);
		feedbackMapper.insert(feedback);
	}



	public void delete(Integer id) {
		feedbackMapper.deleteById(id);
	}

	public void deleteBatch(List<Integer> ids) {
		for (Integer id : ids) {
			feedbackMapper.deleteById(id);
		}
	}

	public void update(Feedback feedback) {
		feedback.setUpdatedAt(LocalDateTime.now());
		feedbackMapper.updateById(feedback);
	}
	public Feedback selectById(Integer id) {
		return feedbackMapper.selectById(id);
	}

	public List<Feedback> selectAll(Feedback feedback) {
		List<Feedback> list=feedbackMapper.selectAll(feedback);
		return list;
	}

	public PageInfo<Feedback> selectPage(Feedback feedback, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		List<Feedback> list = feedbackMapper.selectAll(feedback);
		//会在selectAll使用的sql语句中自动的添加关于分页相关的比如limit参数
		return PageInfo.of(list);
	}

}
