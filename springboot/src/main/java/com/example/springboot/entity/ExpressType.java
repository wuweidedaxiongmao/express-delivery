package com.example.springboot.entity;

import java.time.LocalDateTime;

public class ExpressType {
	private Integer id;
	private String typeName;
	private String price;
	private String description;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public ExpressType() {
	}

	public ExpressType(Integer id, String typeName, String price, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.id = id;
		this.typeName = typeName;
		this.price = price;
		this.description = description;
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
	 * @return typeName
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * 设置
	 * @param typeName
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * 获取
	 * @return price
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * 设置
	 * @param price
	 */
	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * 获取
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
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
		return "ExpressType{id = " + id + ", typeName = " + typeName + ", price = " + price + ", description = " + description + ", createdAt = " + createdAt + ", updatedAt = " + updatedAt + "}";
	}
}
