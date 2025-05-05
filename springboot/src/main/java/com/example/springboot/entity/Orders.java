package com.example.springboot.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

public class Orders {
	private Integer id;                 // 订单ID
	private Integer studentId;          // 学生ID（指向student表的id）
	private Integer courierId;          // 代取员ID（指向student表的id，可为空）
	private Integer typeId;             // 代取类型ID（关联express_type）
	private String pickupAddress;       // 取快递地址
	private Integer addressId;          // 地址ID，快递送达地址
	private String image;               // 快递位置图片
	private String description;         // 订单描述
	private String status;              // 状态（待支付/待接单/已接单，派送申请）
	private LocalDateTime createTime;   // 创建时间
	private LocalDateTime accessTime;   // 订单接收时间
	private LocalDateTime deliveryTime; // 订单送达时间
	private LocalDateTime signTime;    // 订单签收时间
	private BigDecimal rating;         // 评分（保留三位小数）
	private String feedback;           // 反馈信息

	//多表查询数据
	private String studentName;
	private String courierName;
	private String studentUsername;
	private String courierUsername;
	private String typeName;
	private Integer price;
	private String address;//快递送达地址

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getStudentUsername() {
		return studentUsername;
	}

	public void setStudentUsername(String studentUsername) {
		this.studentUsername = studentUsername;
	}

	public String getCourierUsername() {
		return courierUsername;
	}

	public void setCourierUsername(String courierUsername) {
		this.courierUsername = courierUsername;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getCourierName() {
		return courierName;
	}

	public void setCourierName(String courierName) {
		this.courierName = courierName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public Integer getCourierId() {
		return courierId;
	}

	public void setCourierId(Integer courierId) {
		this.courierId = courierId;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getPickupAddress() {
		return pickupAddress;
	}

	public void setPickupAddress(String pickupAddress) {
		this.pickupAddress = pickupAddress;
	}

	public Integer getAddressId() {
		return addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public LocalDateTime getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(LocalDateTime accessTime) {
		this.accessTime = accessTime;
	}

	public LocalDateTime getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(LocalDateTime deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public LocalDateTime getSignTime() {
		return signTime;
	}

	public void setSignTime(LocalDateTime signTime) {
		this.signTime = signTime;
	}

	public BigDecimal getRating() {
		return rating;
	}

	public void setRating(BigDecimal rating) {
		this.rating = rating != null ?
			rating.setScale(3, RoundingMode.HALF_UP) : null;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}



}
