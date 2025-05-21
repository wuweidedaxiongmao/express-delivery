package com.example.springboot.entity;

public class Rank {
  private String username;
  private Integer rating;
  private Integer orderCount;
  private String role;



  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Integer getRating() {
    return rating;
  }

  public void setRating(Integer rating) {
    this.rating = rating;
  }

  public Integer getOrderCount() {
    return orderCount;
  }

  public void setOrderCount(Integer orderCount) {
    this.orderCount = orderCount;
  }
  public String getRole() { return role; }
  public void setRole(String role) { this.role = role; }
}
