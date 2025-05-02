package com.example.springboot.entity;

import java.time.LocalDateTime;

public class CourierCommission {
	private Integer id;
	private Integer courierLevel;  //代取员等级
	private Double commissionRate;  //体现比例
	private String info;  //说明
	//没有添加，createAt字段和updateAt字段

	public CourierCommission() {
	}

	public CourierCommission(Integer id, Integer courierLevel, Double commissionRate, String info) {
		this.id = id;
		this.courierLevel = courierLevel;
		this.commissionRate = commissionRate;
		this.info = info;
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
	 * @return courierLevel
	 */
	public Integer getCourierLevel() {
		return courierLevel;
	}

	/**
	 * 设置
	 * @param courierLevel
	 */
	public void setCourierLevel(Integer courierLevel) {
		this.courierLevel = courierLevel;
	}

	/**
	 * 获取
	 * @return commissionRate
	 */
	public Double getCommissionRate() {
		return commissionRate;
	}

	/**
	 * 设置
	 * @param commissionRate
	 */
	public void setCommissionRate(Double commissionRate) {
		this.commissionRate = commissionRate;
	}

	/**
	 * 获取
	 * @return info
	 */
	public String getInfo() {
		return info;
	}

	/**
	 * 设置
	 * @param info
	 */
	public void setInfo(String info) {
		this.info = info;
	}

	public String toString() {
		return "CourierCommission{id = " + id + ", courierLevel = " + courierLevel + ", commissionRate = " + commissionRate + ", info = " + info + "}";
	}
}
