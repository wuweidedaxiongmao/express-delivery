package com.example.springboot.entity;

public class Account {
	private String username;
	private String password;
	private String role;

	public Account() {
	}

	public Account(String username, String password, String role) {
		this.username = username;
		this.password = password;
		this.role = role;
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

	public String toString() {
		return "Account{username = " + username + ", password = " + password + ", role = " + role + "}";
	}
}
