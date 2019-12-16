package com.atlantis.controller;

import com.atlantis.domain.Users;
import com.atlantis.domain.vo.UsersVO;
import com.atlantis.service.UserService;
import com.atlantis.utils.MD5Utils;
import com.atlantis.utils.ResultByJSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @Auther: Atlantis
 * @Date: 2019/12/3
 * @Description: 用户注册登录控制层
 */
@RestController
@Api(value = "用户注册登录接口", tags = "用户基本操作的Controller")
public class RegisterLoginController extends BasicController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户注册", notes = "提示：账号和密码不能为空，且不可重复注册")
    @PostMapping("/register")
    public ResultByJSON register(@RequestBody Users user) throws Exception {
        String username = user.getUsername();
        String password = user.getPassword();

        // 用户的账号和密码不能为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return ResultByJSON.errorMsg("账号和密码不能为空");
        }
        // 判断用户账号是否存在
        boolean flag = userService.queryUsernameIsExist(username);
        if (!flag) {
            user.setNickname(username); // 默认用户昵称为用户账号
            user.setPassword(MD5Utils.getMD5Str(password)); // 密码加密
            user.setFansCounts(0);
            user.setReceiveLikeCounts(0);
            user.setFollowCounts(0);
            userService.saveUser(user);
            // 设置用户Token
            UsersVO usersVO = setUserRedisSessionOnToken(user);
            return ResultByJSON.ok(usersVO);
        } else {
            return ResultByJSON.errorMsg("此账号已存在");
        }
    }

    @ApiOperation(value = "用户登录", notes = "提示：账号和密码不能为空")
    @PostMapping("/login")
    public ResultByJSON login(@RequestBody Users user) throws Exception {
        String username = user.getUsername();
        String password = user.getPassword();

        // 用户的账号和密码不能为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return ResultByJSON.errorMsg("账号和密码不能为空");
        }

        // 判断用户账号密码是否正确
        Users userResult = userService.queryUserForLogin(username, MD5Utils.getMD5Str(password));
        if (userResult != null) {
            // 设置用户Token
            UsersVO usersVO = setUserRedisSessionOnToken(userResult);
            return ResultByJSON.ok(usersVO);
        } else {
            return ResultByJSON.errorMsg("账号或密码错误");
        }
    }

    @ApiOperation(value = "用户注销", notes = "注销用户登录信息，清除用户所属的USER_REDIS_SESSION")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String", paramType = "query")
    @PostMapping("/logout")
    public ResultByJSON logout(String userId) throws Exception {
        // 判断用户ID是否存在
        if (!(userService.queryUserIDIsExist(userId))) {
            return ResultByJSON.errorException("此用户ID不存在");
        }
        // 清除用户所属的USER_REDIS_SESSION
        redis.del(USER_REDIS_SESSION + ":" + userId);
        return ResultByJSON.ok();
    }

    // 设置用户Token：无状态的Session，身份信息
    public UsersVO setUserRedisSessionOnToken(Users userModel) {
        // 使用唯一值Token来表示该用户身份信息
        String uniqueToken = UUID.randomUUID().toString();
        redis.set(USER_REDIS_SESSION + ":" + userModel.getId(), uniqueToken, 1000 * 60 * 10);
        UsersVO usersVO = new UsersVO();
        // 将user对象复制到usersVO对象中
        BeanUtils.copyProperties(userModel, usersVO);
        usersVO.setUserToken(uniqueToken);
        return usersVO;
    }
}