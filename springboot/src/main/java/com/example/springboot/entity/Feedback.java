package com.example.springboot.entity;

import java.time.LocalDateTime;

public class Feedback {
	private Integer id;         // 反馈ID
	private Integer userId;     // 用户ID（指向 student 表的 id）
	private String userType;    // 用户类型（student/courier）
	private String content;     // 反馈内容
	private String status;      // 状态（待处理/已解决）
	private LocalDateTime createdAt; // 提交时间
	private LocalDateTime updatedAt; // 更新时间（处理时间）

}
