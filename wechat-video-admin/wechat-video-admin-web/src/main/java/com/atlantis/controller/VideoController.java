package com.atlantis.controller;

import com.atlantis.domain.Bgm;
import com.atlantis.domain.Videos;
import com.atlantis.enums.VideoStatusEnum;
import com.atlantis.service.VideoService;
import com.atlantis.utils.ResultByJSON;
import com.atlantis.utils.ResultByPage;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

@Controller
@RequestMapping("video")
public class VideoController {
    @Autowired
    private VideoService videoService;

    // 文件保存的命名空间
    private static final String FILESPACE = "D:/wechat-video-filespace";
    // private static final String FILESPACE = "/usr/local/MyProject/wechat-video/wechat-video-filespace";


    @GetMapping("/showReportList")
    public String showReportList() {
        return "video/reportList";
    }

    @PostMapping("/reportList")
    @ResponseBody
    public ResultByPage reportList(Integer page) {
        ResultByPage result = videoService.queryReportList(page, 10);
        return result;
    }

    @PostMapping("/forbidVideo")
    @ResponseBody
    public ResultByJSON forbidVideo(String videoId) {
        videoService.updateVideoStatus(videoId, VideoStatusEnum.FORBID.value);
        return ResultByJSON.ok();
    }

    @GetMapping("/showVideoList")
    public String showVideoList() {
        return "video/videoList";
    }

    @PostMapping("/list")
    @ResponseBody
    public ResultByPage list(Videos videos, Integer page) {
        ResultByPage result = videoService.queryVideos(videos, page == null ? 1 : page, 10);
        return result;
    }

    @PostMapping("/updateVideoStatus")
    @ResponseBody
    public ResultByJSON updateVideoStatus(String videoId, String status) {
        if (StringUtils.isNotBlank(status) && StringUtils.isNotBlank(videoId)) {
            if ("1".equals(status)) {
                videoService.updateVideoStatus(videoId, VideoStatusEnum.SUCCESS.value);
            } else {
                videoService.updateVideoStatus(videoId, VideoStatusEnum.FORBID.value);
            }
        }
        return ResultByJSON.ok();
    }

    @GetMapping("/showBgmList")
    public String showBgmList() {
        return "video/bgmList";
    }

    @PostMapping("/queryBgmList")
    @ResponseBody
    public ResultByPage queryBgmList(Integer page) {
        return videoService.queryBgmList(page, 10);
    }

    @GetMapping("/showAddBgm")
    public String showAddBgm() {
        return "video/addBgm";
    }

    @PostMapping("/addBgm")
    @ResponseBody
    public ResultByJSON addBgm(Bgm bgm) {
        videoService.addBgm(bgm);
        return ResultByJSON.ok();
    }

    @PostMapping("/delBgm")
    @ResponseBody
    public ResultByJSON delBgm(String bgmId) {
        videoService.deleteBgm(bgmId);
        return ResultByJSON.ok();
    }

    @PostMapping("/bgmUpload")
    @ResponseBody
    public ResultByJSON bgmUpload(@RequestParam("file") MultipartFile[] files) throws Exception {
        // 文件保存的命名空间
        String fileSpace = FILESPACE + "/bgm";
        // 保存到数据库中的相对路径
        String uploadPathDB = "/bgm";

        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        try {
            if (files != null && files.length > 0) {

                String fileName = files[0].getOriginalFilename();
                if (StringUtils.isNotBlank(fileName)) {
                    // 文件上传的最终保存路径
                    String finalPath = fileSpace + "/" + fileName;
                    // 设置数据库保存的路径
                    uploadPathDB += ("/" + fileName);

                    File outFile = new File(finalPath);
                    if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                        // 创建父文件夹
                        outFile.getParentFile().mkdirs();
                    }
                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = files[0].getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                }
            } else {
                return ResultByJSON.errorMsg("上传出错...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultByJSON.errorMsg("上传出错...");
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }
        return ResultByJSON.ok(uploadPathDB);
    }
}