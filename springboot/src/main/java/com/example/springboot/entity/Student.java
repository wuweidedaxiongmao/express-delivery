package com.example.springboot.entity;

import java.time.LocalDateTime;

public class Student extends Account{
	private Integer id;
	private String username;
	private String password;
	private String name;
	private String avatar;
	private String phone;
	private String email;
	private String role;

	private Integer money; //余额
	private Integer addMoney; //工资(额外)

	private Integer level; //代取员等级
	private Double rating; // 平均评分
	private Integer ratingCount;
	private Integer orderCount;  // 接单数量
	private String problem; //admin拉黑代取员时记录的信息
	private Integer ifBlack;//是否拉黑，1表示被拉黑

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public Integer getAddMoney() {
		return addMoney;
	}

	public void setAddMoney(Integer addMoney) {
		this.addMoney = addMoney;
	}

	// 这三个变量只有在role==COUR才有效
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;


	/**
	 * 获取
	 * @return id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 设置
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 获取
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 设置
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 获取
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 获取
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取
	 * @return avatar
	 */
	public String getAvatar() {
		return avatar;
	}

	/**
	 * 设置
	 * @param avatar
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	/**
	 * 获取
	 * @return phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * 设置
	 * @param phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 获取
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 设置
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 获取
	 * @return role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * 设置
	 * @param role
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * 获取
	 * @return level
	 */
	public Integer getLevel() {
		return level;
	}

	/**
	 * 设置
	 * @param level
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}

	/**
	 * 获取
	 * @return rating
	 */
	public Double getRating() {
		return rating;
	}

	/**
	 * 设置
	 * @param rating
	 */
	public void setRating(Double rating) {
		this.rating = rating;
	}

	/**
	 * 获取
	 * @return orderCount
	 */
	public Integer getOrderCount() {
		return orderCount;
	}

	/**
	 * 设置
	 * @param orderCount
	 */
	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}

	/**
	 * 获取
	 * @return createdAt
	 */
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	/**
	 * 设置
	 * @param createdAt
	 */
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * 获取
	 * @return updatedAt
	 */
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * 设置
	 * @param updatedAt
	 */
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Integer getRatingCount() {
		return ratingCount;
	}

	public void setRatingCount(Integer ratingCount) {
		this.ratingCount = ratingCount;
	}

	public String getProblem() {
		return problem;
	}

	public void setProblem(String problem) {
		this.problem = problem;
	}

	public Integer getIfBlack() {
		return ifBlack;
	}

	public void setIfBlack(Integer ifBlack) {
		this.ifBlack = ifBlack;
	}
}
