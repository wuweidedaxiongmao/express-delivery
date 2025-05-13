package com.example.springboot.service;

import com.example.springboot.entity.Announcement;
import com.example.springboot.mapper.AnnouncementMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnnouncementService {

  @Resource
  private AnnouncementMapper announcementMapper;

  /**
   * 新增公告
   */
  public void add(Announcement announcement) {
    LocalDateTime now = LocalDateTime.now();
    announcement.setCreatedAt(now);
    announcement.setUpdatedAt(now);
    announcementMapper.insert(announcement);
  }

  /**
   * 根据ID删除公告
   */
  public void delete(Integer id) {
    announcementMapper.deleteById(id);
  }

  /**
   * 批量删除公告
   */
  public void deleteBatch(List<Integer> ids) {
    for (Integer id : ids) {
      announcementMapper.deleteById(id);
    }
  }

  /**
   * 更新公告
   */
  public void update(Announcement announcement) {
    announcement.setUpdatedAt(LocalDateTime.now());
    announcementMapper.updateById(announcement);
  }

  /**
   * 根据ID查询公告
   */
  public Announcement selectById(Integer id) {
    return announcementMapper.selectById(id);
  }

  /**
   * 根据条件查询所有公告
   */
  public List<Announcement> selectAll(Announcement announcement) {
    return announcementMapper.selectAll(announcement);
  }

  /**
   * 分页查询公告
   */
  public PageInfo<Announcement> selectPage(Announcement announcement, Integer pageNum, Integer pageSize) {
    PageHelper.startPage(pageNum, pageSize);
    List<Announcement> list = announcementMapper.selectAll(announcement);
    return PageInfo.of(list);
  }


  /**
   * 获取所有公告，按创建时间倒序
   */
  public List<Announcement> selectAllOrdered() {
    return announcementMapper.selectAllOrdered();
  }
}
