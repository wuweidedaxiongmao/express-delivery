package com.example.springboot.mapper;

import com.example.springboot.entity.Announcement;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AnnouncementMapper {
  /**
   * 根据条件查询所有公告
   * @param announcement 查询条件实体
   * @return 公告列表
   */
  List<Announcement> selectAll(Announcement announcement);

  /**
   * 根据ID查询单条公告
   * @param id 公告ID
   * @return Announcement实体
   */
  @Select("SELECT * FROM announcement WHERE id = #{id}")
  Announcement selectById(Integer id);

  /**
   * 插入一条新公告
   * @param announcement 公告实体
   */
  void insert(Announcement announcement);

  /**
   * 根据ID更新公告
   * @param announcement 公告实体（需包含ID）
   */
  void updateById(Announcement announcement);

  /**
   * 根据ID删除公告
   * @param id 公告ID
   */
  @Delete("DELETE FROM announcement WHERE id = #{id}")
  void deleteById(Integer id);

  @Select("SELECT * FROM announcement ORDER BY created_at DESC")
  List<Announcement> selectAllOrdered();
}
