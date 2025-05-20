package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.entity.Rank;
import com.example.springboot.service.RankService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rank")
public class RankController {

  @Resource
  private RankService rankService;

  /**
   * 查询排行榜，支持排序字段与方向
   */
  @GetMapping("/list")
  public Result getRankList(
    @RequestParam(defaultValue = "rating") String orderBy,
    @RequestParam(defaultValue = "desc") String orderDir
  ) {
    List<Rank> list = rankService.getRankedList(orderBy, orderDir);
    return Result.success(list);
  }
}
