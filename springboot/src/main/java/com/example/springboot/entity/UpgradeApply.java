package com.example.springboot.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UpgradeApply {
	private Integer id;
	private Integer courierId;
	private Integer currentLevel;
	private Integer targetLevel;
	private String description;
	private String status;
	private Integer adminId;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	//多表查询，从student表中查询的数据
	private String courierUsername;
	private String courierName;
	private Double rating; //平均评分
	private Integer orderCount; //接单数量

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

	public Integer getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentLevel(Integer currentLevel) {
		this.currentLevel = currentLevel;
	}

	public Integer getTargetLevel() {
		return targetLevel;
	}

	public void setTargetLevel(Integer targetLevel) {
		this.targetLevel = targetLevel;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getCreatedAtStr() {
		if (createdAt != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			return createdAt.format(formatter);
		}
		return null;
	}

	public String getUpdateAtStr(){
		if(updatedAt!=null){
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			return updatedAt.format(formatter);
		}
		return null;
	}

	public String getCourierUsername() {
		return courierUsername;
	}

	public void setCourierUsername(String courierUsername) {
		this.courierUsername = courierUsername;
	}

	public String getCourierName() {
		return courierName;
	}

	public void setCourierName(String courierName) {
		this.courierName = courierName;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public Integer getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}
}
