package com.example.springboot.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReleaseApply {
	/**
	 * 申请ID
	 */
	private Integer id;

	/**
	 * 代取员ID（指向student表的id）
	 */
	private Integer courierId;

	/**
	 * 代取员用户名
	 */
	private String username;

	/**
	 * 代取员姓名
	 */
	private String name;

	/**
	 * 代取员问题，与student表中problem字段一致，富文本
	 */
	private String problem;

	/**
	 * 解封原因，代取员提交理由，富文本
	 */
	private String reason;

	/**
	 * 状态（待审核/已通过/已拒绝）
	 * 用ReviewStatus类中的常量表示
	 */
	private String status;

	/**
	 * 审核管理员ID
	 */
	private Integer adminId;

	/**
	 * 申请时间
	 */
	private LocalDateTime createdAt;

	/**
	 * 更新时间（审核时间）
	 */
	private LocalDateTime updatedAt;

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

	public Integer getCourierId() {
		return courierId;
	}

	public void setCourierId(Integer courierId) {
		this.courierId = courierId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProblem() {
		return problem;
	}

	public void setProblem(String problem) {
		this.problem = problem;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
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
}
