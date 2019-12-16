package com.atlantis.controller;

import com.atlantis.domain.Users;
import com.atlantis.domain.UsersReport;
import com.atlantis.domain.vo.PublisherVideo;
import com.atlantis.domain.vo.UsersVO;
import com.atlantis.service.UserService;
import com.atlantis.service.VideoService;
import com.atlantis.utils.ResultByJSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @Auther: Atlantis
 * @Date: 2019/12/3
 * @Description: 用户相关控制层
 */
@RestController
@Api(value = "用户相关业务接口", tags = "用户相关业务的Controller")
@RequestMapping("/user")
public class UserController extends BasicController {
    @Autowired
    private UserService userService;

    @Autowired
    private VideoService videoService;

    @ApiOperation(value = "用户头像上传", notes = "可在Swagger2进行接口调试")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String", paramType = "query")
    @PostMapping(value = "/uploadFace", headers = "content-type=multipart/form-data")
    public ResultByJSON uploadFace(String userId, MultipartFile file) throws Exception {
        if (StringUtils.isBlank(userId)) {
            return ResultByJSON.errorException("用户ID不能为空");
        }
        // 判断用户ID是否存在
        if (!(userService.queryUserIDIsExist(userId))) {
            return ResultByJSON.errorException("此用户ID不存在");
        }

        // 保存到数据库中的相对路径地址
        String uploadFacePath = "/" + userId + "/face/";
        // 文件上传到本地磁盘的路径地址
        String finalFacePath = "";

        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        try {
            // 判断上传的文件是否有效
            if (file != null) {
                String filename = file.getOriginalFilename();
                if (StringUtils.isNotBlank(filename)) {
                    finalFacePath = FileSpace + uploadFacePath + filename;
                    uploadFacePath += filename;
                    File outFile = new File(finalFacePath);
                    if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                        outFile.getParentFile().mkdirs(); //创建父文件夹
                    }
                    // 上传头像
                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = file.getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                }
            } else {
                return ResultByJSON.errorException("头像上传失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultByJSON.errorException("头像上传服务繁忙");
        } finally {
            // 关闭资源
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }

        Users users = new Users();
        users.setId(userId);
        users.setFaceImage(uploadFacePath);
        // 头像路径上传至数据库中
        userService.updateUserInfo(users);
        return ResultByJSON.ok(uploadFacePath);
    }

    @ApiOperation(value = "获取用户信息", notes = "可在Swagger2进行接口调试")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "查询某用户的ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "fanId", value = "访问者的ID", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping("/queryUserInfo")
    public ResultByJSON queryUserInfo(String userId, String fanId) throws Exception {
        if (StringUtils.isBlank(userId)) {
            return ResultByJSON.errorException("用户ID不能为空");
        }
        // 判断用户ID是否存在
        if (!(userService.queryUserIDIsExist(userId))) {
            return ResultByJSON.errorException("此用户ID不存在");
        }
        Users userInfo = userService.queryUserInfo(userId);
        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(userInfo, usersVO);
        usersVO.setFollow(userService.queryIfFollow(userId, fanId));
        return ResultByJSON.ok(usersVO);
    }

    @ApiOperation(value = "查询视频发布者的信息", notes = "可在Swagger2进行接口调试")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginUserId", value = "当前登录用户的ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "videoId", value = "视频ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "publishUserId", value = "视频发布者的ID", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping("/queryPublisher")
    public ResultByJSON queryPublisher(String loginUserId, String videoId, String publishUserId) throws Exception {
        if (StringUtils.isBlank(loginUserId) && StringUtils.isBlank(videoId) && StringUtils.isBlank(publishUserId)) {
            return ResultByJSON.errorException("参数ID不能为空");
        }
        // 判断ID是否存在
        if (!(userService.queryUserIDIsExist(loginUserId)) && !(userService.queryUserIDIsExist(publishUserId)) && !(videoService.queryVideoIDIsExist(videoId))) {
            return ResultByJSON.errorException("填写的参数ID不存在");
        }

        // 查询视频发布者的信息
        Users userInfo = userService.queryUserInfo(publishUserId);
        UsersVO publisher = new UsersVO();
        BeanUtils.copyProperties(userInfo, publisher);

        // 查询当前登录者和视频的点赞关系
        boolean userLikeVideo = userService.isUserLikeVideo(loginUserId, videoId);
        PublisherVideo bean = new PublisherVideo();
        bean.setPublisher(publisher);
        bean.setUserLikeVideo(userLikeVideo);
        return ResultByJSON.ok(bean);
    }

    @ApiOperation(value = "关注某用户，成为该用户的粉丝")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "被关注的用户", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "fanId", value = "登录者", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping("/beYourFans")
    public ResultByJSON beYourFans(String userId, String fanId) throws Exception {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(fanId)) {
            return ResultByJSON.errorMsg("参数ID不能为空");
        }
        // 判断ID是否存在
        if (!(userService.queryUserIDIsExist(userId)) && !(userService.queryUserIDIsExist(fanId))) {
            return ResultByJSON.errorException("填写的参数ID不存在");
        }
        userService.saveUserFanRelation(userId, fanId);
        return ResultByJSON.ok("关注成功");
    }

    @ApiOperation(value = "取消关注某用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "被关注的用户", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "fanId", value = "登录者", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping("/notYourFans")
    public ResultByJSON notYourFans(String userId, String fanId) throws Exception {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(fanId)) {
            return ResultByJSON.errorMsg("");
        }
        // 判断ID是否存在
        if (!(userService.queryUserIDIsExist(userId)) && !(userService.queryUserIDIsExist(fanId))) {
            return ResultByJSON.errorException("填写的参数ID不存在");
        }
        userService.deleteUserFanRelation(userId, fanId);
        return ResultByJSON.ok("取消关注");
    }

    @ApiOperation(value = "举报某用户", notes = "只可通过微信小程序端进行测试")
    @ApiImplicitParam(name = "usersReport", value = "举报信息,json格式", required = true, dataType = "String", paramType = "query")
    @PostMapping("/reportUser")
    public ResultByJSON reportUser(@RequestBody UsersReport usersReport) throws Exception {
        // 保存举报信息
        userService.reportUser(usersReport);
        return ResultByJSON.errorMsg("举报成功,我们会及时审核");
    }
}
