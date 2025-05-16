package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.entity.Announcement;
import com.example.springboot.service.AnnouncementService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/announcement")
public class AnnouncementController {

  @Resource
  private AnnouncementService announcementService;

  /**
   * 新增公告
   */
  @PostMapping("/add")
  public Result add(@RequestBody Announcement announcement) {
    announcementService.add(announcement);
    return Result.success();
  }

  /**
   * 根据ID删除公告
   */
  @DeleteMapping("/deleteById")
  public Result deleteById(@RequestParam Integer id) {
    announcementService.delete(id);
    return Result.success();
  }

  /**
   * 批量删除公告
   */
  @DeleteMapping("/deleteBatch")
  public Result deleteBatch(@RequestBody List<Integer> ids) {
    announcementService.deleteBatch(ids);
    return Result.success();
  }

  /**
   * 更新公告
   */
  @PutMapping("/update")
  public Result update(@RequestBody Announcement announcement) {
    announcementService.update(announcement);
    return Result.success();
  }

  /**
   * 根据ID查询公告
   */
  @GetMapping("/selectById/{id}")
  public Result selectById(@PathVariable Integer id) {
    Announcement announcement = announcementService.selectById(id);
    return Result.success(announcement);
  }

  /**
   * 根据条件查询所有公告
   */
  @GetMapping("/selectAll")
  public Result selectAll(Announcement announcement) {
    List<Announcement> list = announcementService.selectAll(announcement);
    return Result.success(list);
  }

  /**
   * 分页查询公告
   */
  @GetMapping("/selectPage")
  public Result selectPage(
    Announcement announcement,
    @RequestParam(defaultValue = "1") Integer pageNum,
    @RequestParam(defaultValue = "10") Integer pageSize) {
    PageInfo<Announcement> pageInfo = announcementService.selectPage(announcement, pageNum, pageSize);
    return Result.success(pageInfo);
  }

  private static final Logger logger = LoggerFactory.getLogger(AnnouncementController.class);


  @GetMapping("/latest")
  public ResponseEntity<List<Announcement>> getLatest() {
    List<Announcement> list = announcementService.selectAllOrdered();
    logger.info("Fetched announcements from DB: {}", list);
    return ResponseEntity.ok(list);
  }
}
