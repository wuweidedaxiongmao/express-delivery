package com.example.springboot.entity;

import java.time.LocalDateTime;

public class Admin extends Account{

	private Integer id;
	private String username;
	private String password;
	private String name;
	private String avatar;
	private String role;
	private String phone;
	private String email;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public Admin() {
	}

	public Admin(Integer id, String username, String password, String name, String avatar, String role, String phone, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.avatar = avatar;
		this.role = role;
		this.phone = phone;
		this.email = email;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

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

	public String toString() {
		return "Admin{id = " + id + ", username = " + username + ", password = " + password + ", name = " + name + ", avatar = " + avatar + ", role = " + role + ", phone = " + phone + ", email = " + email + ", createdAt = " + createdAt + ", updatedAt = " + updatedAt + "}";
	}
}
