package com.example.springboot.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Feedback {
	private Integer id;         // 反馈ID
	private Integer userId;     // 用户ID（指向 student 表的 id）
	private String userType;    // 用户类型（student/courier）
	private String content;     // 反馈内容
	private String status;      // 状态（待处理/已解决）
	private LocalDateTime createdAt; // 提交时间
	private LocalDateTime updatedAt; // 更新时间（处理时间）

	//多表查询
	private String studentName;
	private String studentUsername;

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	public String getCreatedAtStr(){
		if(createdAt!=null){
			return createdAt.format(formatter);
		}
		return null;
	}
	public String getUpdatedAtStr(){
		if(updatedAt!=null){
			return updatedAt.format(formatter);
		}
		return null;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStudentUsername() {
		return studentUsername;
	}

	public void setStudentUsername(String studentUsername) {
		this.studentUsername = studentUsername;
	}
}
