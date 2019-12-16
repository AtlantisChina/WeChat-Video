package com.atlantis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import com.atlantis.utils.RedisOperator;

/**
 * @Auther: Atlantis
 * @Date: 2019/12/5
 * @Description: 所有Controller控制的父类，全局属性
 * @version: 1.0
 */
@RestController
public class BasicController {
    @Autowired
    public RedisOperator redis;

    // 设置用户Token：无状态Session,身份信息
    public static final String USER_REDIS_SESSION = "user-redis-session";

    // 分页查询，每页的数量
    public static final Integer PAGE_SIZE = 6;

    // 用户资源路径
    @Value("${FileSpace}")
    public String FileSpace;

    // ffmpeg路径
    @Value("${FFMpegURL}")
    public String FFMpegURL;
}