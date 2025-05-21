package com.example.springboot.service;

import com.example.springboot.entity.Rank;
import com.example.springboot.mapper.RankMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankService {

  @Resource
  private RankMapper rankMapper;

  public List<Rank> getRankedList(String orderBy, String orderDir) {
    return rankMapper.selectAllOrdered(orderBy, orderDir);
  }
}
