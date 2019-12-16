package com.atlantis.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import javax.persistence.*;

@ApiModel(value = "Videos", description = "视频实体类")
public class Videos {
    @ApiModelProperty(hidden = true)
    @Id
    private String id;

    @ApiModelProperty(value = "发布者ID（用户id）", name = "userId", required = true)
    @Column(name = "user_id")
    private String userId; // 发布者ID（用户id）

    @ApiModelProperty(value = "用户使用BGM的ID", name = "bgmId")
    @Column(name = "bgm_id")
    private String bgmId; // 用户使用BGM的信息

    @ApiModelProperty(value = "视频描述", name = "videoDesc")
    @Column(name = "video_desc")
    private String videoDesc;  // 视频描述

    @ApiModelProperty(value = "视频存放的路径", name = "videoPath", required = true)
    @Column(name = "video_path")
    private String videoPath; // 视频存放的路径

    @ApiModelProperty(value = "视频长度（秒）", name = "videoSeconds", required = true)
    @Column(name = "video_seconds")
    private Float videoSeconds; // 视频长度（秒）

    @ApiModelProperty(value = "视频宽度", name = "videoWidth", required = true)
    @Column(name = "video_width")
    private Integer videoWidth; // 视频宽度

    @ApiModelProperty(value = "视频高度", name = "videoHeight", required = true)
    @Column(name = "video_height")
    private Integer videoHeight; // 视频高度

    @ApiModelProperty(value = "视频封面图路径", name = "coverPath", required = true)
    @Column(name = "cover_path")
    private String coverPath; // 视频封面图

    @ApiModelProperty(value = "收藏/赞的数量", name = "likeCounts")
    @Column(name = "like_counts")
    private Long likeCounts; // 收藏/赞的数量

    @ApiModelProperty(hidden = true)
    private Integer status; //视频状态：1为发布成功;2为禁止播放，管理员操作

    @ApiModelProperty(value = "创建时间", name = "createTime")
    @Column(name = "create_time")
    private Date createTime; // 创建时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBgmId() {
        return bgmId;
    }

    public void setBgmId(String bgmId) {
        this.bgmId = bgmId;
    }

    public String getVideoDesc() {
        return videoDesc;
    }

    public void setVideoDesc(String videoDesc) {
        this.videoDesc = videoDesc;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public Float getVideoSeconds() {
        return videoSeconds;
    }

    public void setVideoSeconds(Float videoSeconds) {
        this.videoSeconds = videoSeconds;
    }

    public Integer getVideoWidth() {
        return videoWidth;
    }

    public void setVideoWidth(Integer videoWidth) {
        this.videoWidth = videoWidth;
    }

    public Integer getVideoHeight() {
        return videoHeight;
    }

    public void setVideoHeight(Integer videoHeight) {
        this.videoHeight = videoHeight;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public Long getLikeCounts() {
        return likeCounts;
    }

    public void setLikeCounts(Long likeCounts) {
        this.likeCounts = likeCounts;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}