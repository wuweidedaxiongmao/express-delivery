package com.example.springboot.mapper;

import com.example.springboot.entity.Rank;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RankMapper {
  List<Rank> selectAllOrdered(
    @Param("orderBy") String orderBy,
    @Param("orderDir") String orderDir
  );
}
