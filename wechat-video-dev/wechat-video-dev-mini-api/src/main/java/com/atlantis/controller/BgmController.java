package com.atlantis.controller;

import com.atlantis.service.BgmService;
import com.atlantis.utils.ResultByJSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: Atlantis
 * @Date: 2019/12/3
 * @Description: 背景音乐控制层
 */
@RestController
@Api(value = "背景音乐接口", tags = "背景音乐的Controller")
@RequestMapping("/bgm")
public class BgmController extends BasicController {
    @Autowired
    private BgmService bgmService;

    @ApiOperation(value = "查询所有背景音乐", notes = "可在Swagger2进行接口调试")
    @PostMapping("/queryBgmList")
    public ResultByJSON queryBgmList() throws Exception {
        return ResultByJSON.ok(bgmService.queryBgmList());
    }
}
