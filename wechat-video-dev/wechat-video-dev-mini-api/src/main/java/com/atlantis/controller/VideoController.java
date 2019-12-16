package com.atlantis.controller;

import com.atlantis.domain.Bgm;
import com.atlantis.domain.Comments;
import com.atlantis.domain.Videos;
import com.atlantis.enums.VideoStatusEnum;
import com.atlantis.service.BgmService;
import com.atlantis.service.UserService;
import com.atlantis.service.VideoService;
import com.atlantis.utils.FFMpegUtil;
import com.atlantis.utils.ResultByJSON;
import com.atlantis.utils.ResultByPage;
import io.swagger.annotations.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

/**
 * @Auther: Atlantis
 * @Date: 2019/12/3
 * @Description: 视频控制层
 */
@RestController
@Api(value = "视频相关业务接口", tags = "视频相关业务的Controller")
@RequestMapping("/video")
public class VideoController extends BasicController {
    @Autowired
    private VideoService videoService;

    @Autowired
    private BgmService bgmService;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "视频上传", notes = "只可通过微信小程序端进行测试")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "bgmId", value = "背景音乐ID", required = false, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "videoSeconds", value = "视频播放长度", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "videoWidth", value = "视频宽度", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "videoHeight", value = "视频高度", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "videoDesc", value = "视频描述", required = false, dataType = "String", paramType = "form")
    })
    @PostMapping(value = "/uploadVideo", headers = "content-type=multipart/form-data")
    public ResultByJSON uploadVideo(String userId, String bgmId, double videoSeconds,
                                    int videoWidth, int videoHeight, String videoDesc,
                                    @ApiParam(value = "短视频", required = true) MultipartFile file) throws Exception {
        if (StringUtils.isBlank(userId)) {
            return ResultByJSON.errorException("用户ID不能为空");
        }
        // 判断用户ID是否存在
        if (!(userService.queryUserIDIsExist(userId))) {
            return ResultByJSON.errorException("此用户ID不存在");
        }

        // 保存到数据库中的相对路径地址
        String uploadVideoPath = "/" + userId + "/video/";
        String uploadVideoImagePath = "/" + userId + "/video/"; // 视频预览图
        // 文件上传到本地磁盘的路径地址
        String finalVideoPath = "";

        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        try {
            // 判断上传的文件是否有效
            if (file != null) {
                String filename = file.getOriginalFilename();
                // fix bug: 解决小程序端OK，PC端不OK的bug，原因：PC端和小程序端对临时视频的命名不同
                // String fileNamePrefix = fileName.split("\\.")[0];
                String arrayFilenameItem[] = filename.split("\\.");
                String fileNamePrefix = "";
                for (int i = 0; i < arrayFilenameItem.length - 1; i++) {
                    fileNamePrefix += arrayFilenameItem[i];
                }
                if (StringUtils.isNotBlank(filename)) {
                    finalVideoPath = FileSpace + uploadVideoPath + filename;
                    uploadVideoPath += filename;
                    uploadVideoImagePath = uploadVideoImagePath + fileNamePrefix + ".jpg";
                    File outFile = new File(finalVideoPath);
                    if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                        outFile.getParentFile().mkdirs(); //创建父文件夹
                    }
                    // 上传视频到本地磁盘
                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = file.getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                }
            } else {
                return ResultByJSON.errorException("视频上传失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultByJSON.errorException("视频上传服务繁忙");
        } finally {
            // 关闭资源
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }

        // 创建FFMpeg工具类对象
        FFMpegUtil tool = new FFMpegUtil(FFMpegURL);
        // 判断bgmId是否为空，如果不为空，那就查询bgm的信息，并且合并视频，生产新的视频
        if (StringUtils.isNotBlank(bgmId)) {
            Bgm bgm = bgmService.queryBgmById(bgmId);
            String bgmInputPath = FileSpace + bgm.getPath();

            String videoInputPath = finalVideoPath;
            String videoOutputName = UUID.randomUUID().toString() + ".mp4";
            uploadVideoPath = "/" + userId + "/video" + "/" + videoOutputName;
            // 音频合并后的磁盘路径
            finalVideoPath = FileSpace + uploadVideoPath;
            tool.mergeVideoBgm(bgmInputPath, videoInputPath, videoSeconds, finalVideoPath);
        }
        //System.out.println("uploadVideoPath=" + uploadVideoPath);
        //System.out.println("finalVideoPath=" + finalVideoPath);

        // 对视频进行截图
        tool.getVideoImage(finalVideoPath, FileSpace + uploadVideoImagePath);

        // 保存视频信息至数据库
        Videos video = new Videos();
        video.setBgmId(bgmId);
        video.setUserId(userId);
        video.setVideoSeconds((float) videoSeconds);
        video.setVideoHeight(videoHeight);
        video.setVideoWidth(videoWidth);
        video.setVideoDesc(videoDesc);
        video.setVideoPath(uploadVideoPath);
        video.setCoverPath(uploadVideoImagePath); // 视频预览图
        video.setStatus(VideoStatusEnum.SUCCESS.value);
        video.setCreateTime(new Date());
        String videoId = videoService.saveVideo(video);
        return ResultByJSON.ok(videoId);
    }

    @ApiOperation(value = "视频封面上传", notes = "该接口已废弃")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "videoId", value = "视频ID", required = true, dataType = "String", paramType = "form")
    })
    @PostMapping(value = "/uploadVideoImage", headers = "content-type=multipart/form-data")
    public ResultByJSON uploadVideoImage(String userId, String videoId,
                                         @ApiParam(value = "视频封面", required = true) MultipartFile file) throws Exception {
        if (StringUtils.isBlank(videoId) || StringUtils.isBlank(userId)) {
            return ResultByJSON.errorException("用户或视频的ID不能为空");
        }

        // 判断用户或视频的ID是否存在
        if (!(userService.queryUserIDIsExist(userId)) && !(videoService.queryVideoIDIsExist(videoId))) {
            return ResultByJSON.errorException("用户或视频的ID不存在");
        }

        // 保存到数据库中的相对路径地址
        String uploadVideoImagePath = "/" + userId + "/video/"; // 视频预览图
        // 文件上传到本地磁盘的路径地址
        String finalVideoImagePath = "";

        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        try {
            // 判断上传的文件是否有效
            if (file != null) {
                String filename = file.getOriginalFilename();
                if (StringUtils.isNotBlank(filename)) {
                    finalVideoImagePath = FileSpace + uploadVideoImagePath + filename;
                    uploadVideoImagePath += filename;
                    File outFile = new File(finalVideoImagePath);
                    if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                        outFile.getParentFile().mkdirs(); //创建父文件夹
                    }
                    // 上传视频封面到本地磁盘
                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = file.getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                }
            } else {
                return ResultByJSON.errorException("视频封面上传失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultByJSON.errorException("视频封面上传服务繁忙");
        } finally {
            // 关闭资源
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }
        videoService.updateVideo(videoId, uploadVideoImagePath);
        return ResultByJSON.ok();
    }

    @ApiOperation(value = "分页和搜索查询视频列表", notes = "可在Swagger2进行接口调试")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "searchText", value = "搜索条件", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "isSaveRecord", value = "是否保存搜索记录:1为保存，0或null为不保存", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "第几页，默认为1", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示多少条数据，默认为5", dataType = "Integer", paramType = "query")
    })
    @PostMapping(value = "/showAllVideos")
    public ResultByJSON showAllVideos(String searchText, Integer isSaveRecord, Integer page, Integer pageSize) throws Exception {
        //System.out.println(searchText);
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }

        ResultByPage resultVideosList = videoService.getAllVideos(searchText, isSaveRecord, page, pageSize);
        return ResultByJSON.ok(resultVideosList);
    }

    @ApiOperation(value = "获取热搜词", notes = "可在Swagger2进行接口调试")
    @PostMapping(value = "/getHotWords")
    public ResultByJSON getHotWords() throws Exception {
        return ResultByJSON.ok(videoService.getHotWords());
    }

    @ApiOperation(value = "我关注的人发的视频")
    @PostMapping("/showMyFollow")
    public ResultByJSON showMyFollow(String userId, Integer page, Integer pageSize) throws Exception {
        if (StringUtils.isBlank(userId)) {
            return ResultByJSON.errorException("用户ID不能为空");
        }
        // 判断用户ID是否存在
        if (!(userService.queryUserIDIsExist(userId))) {
            return ResultByJSON.errorException("此用户ID不存在");
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }

        ResultByPage videosList = videoService.queryMyFollowVideos(userId, page, pageSize);
        return ResultByJSON.ok(videosList);
    }

    @ApiOperation(value = "我收藏(点赞)过的视频列表")
    @PostMapping("/showMyLike")
    public ResultByJSON showMyLike(String userId, Integer page, Integer pageSize) throws Exception {
        if (StringUtils.isBlank(userId)) {
            return ResultByJSON.errorException("用户ID不能为空");
        }
        // 判断用户ID是否存在
        if (!(userService.queryUserIDIsExist(userId))) {
            return ResultByJSON.errorException("此用户ID不存在");
        }
        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }
        ResultByPage videosList = videoService.queryMyLikeVideos(userId, page, pageSize);
        return ResultByJSON.ok(videosList);
    }

    @ApiOperation(value = "收藏(点赞)视频")
    @PostMapping(value = "/userLike")
    public ResultByJSON userLike(String userId, String videoId, String videoCreaterId) throws Exception {
        videoService.userLikeVideo(userId, videoId, videoCreaterId);
        return ResultByJSON.ok();
    }

    @ApiOperation(value = "取消收藏(点赞)视频")
    @PostMapping(value = "/userUnLike")
    public ResultByJSON userUnLike(String userId, String videoId, String videoCreaterId) throws Exception {
        videoService.userUnLikeVideo(userId, videoId, videoCreaterId);
        return ResultByJSON.ok();
    }

    @ApiOperation(value = "视频留言")
    @PostMapping("/saveComment")
    public ResultByJSON saveComment(@RequestBody Comments comment, String fatherCommentId, String toUserId) throws Exception {
        comment.setFatherCommentId(fatherCommentId);
        comment.setToUserId(toUserId);
        videoService.saveComment(comment);
        return ResultByJSON.ok();
    }

    @ApiOperation(value = "分页查询视频留言")
    @PostMapping("/getVideoComments")
    public ResultByJSON getVideoComments(String videoId, Integer page, Integer pageSize) throws Exception {
        if (StringUtils.isBlank(videoId)) {
            return ResultByJSON.ok();
        }
        // 分页查询视频留言列表，时间顺序倒序排序
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }
        ResultByPage list = videoService.getAllComments(videoId, page, pageSize);
        return ResultByJSON.ok(list);
    }
}