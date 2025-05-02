package com.example.springboot.entity;

import org.springframework.scheduling.support.SimpleTriggerContext;

import java.time.LocalDateTime;

public class Identification {
	private Integer id;
	private Integer studentId;
	private String name;
	private String image;
	private String status;
	private Integer adminId;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public Identification() {
	}

	public Identification(Integer id, Integer studentId, String name, String image, String status, Integer adminId, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.id = id;
		this.studentId = studentId;
		this.name = name;
		this.image = image;
		this.status = status;
		this.adminId = adminId;
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
	 * @return studentId
	 */
	public Integer getStudentId() {
		return studentId;
	}

	/**
	 * 设置
	 * @param studentId
	 */
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
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
	 * @return image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * 设置
	 * @param image
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * 获取
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 获取
	 * @return adminId
	 */
	public Integer getAdminId() {
		return adminId;
	}

	/**
	 * 设置
	 * @param adminId
	 */
	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
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
		return "Identification{id = " + id + ", studentId = " + studentId + ", name = " + name + ", image = " + image + ", status = " + status + ", adminId = " + adminId + ", createdAt = " + createdAt + ", updatedAt = " + updatedAt + "}";
	}
}
