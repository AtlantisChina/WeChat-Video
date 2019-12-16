package com.atlantis.domain;

import javax.persistence.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "User", description = "用户实体类")
public class Users {
    @ApiModelProperty(hidden = true)
    @Id
    private String id;

    @ApiModelProperty(value = "账号(唯一)", name = "username", example = "test", required = true)
    private String username; // 用户名

    @ApiModelProperty(value = "密码", name = "password", example = "123", required = true)
    private String password; // 密码

    @ApiModelProperty(hidden = true)
    @Column(name = "face_image")
    private String faceImage; // 头像，如果没有默认给一张

    @ApiModelProperty(hidden = true)
    private String nickname; // 用户昵称

    @ApiModelProperty(hidden = true)
    @Column(name = "fans_counts")
    private Integer fansCounts; // 粉丝数量

    @ApiModelProperty(hidden = true)
    @Column(name = "follow_counts")
    private Integer followCounts; // 关注总数

    @ApiModelProperty(hidden = true)
    @Column(name = "receive_like_counts")
    private Integer receiveLikeCounts; // 收到赞/收藏的数量

    public Users() {
        super();
    }

    public Users(String id, String username, String password, String faceImage, String nickname, Integer fansCounts,
                 Integer followCounts, Integer receiveLikeCounts) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.faceImage = faceImage;
        this.nickname = nickname;
        this.fansCounts = fansCounts;
        this.followCounts = followCounts;
        this.receiveLikeCounts = receiveLikeCounts;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFaceImage() {
        return faceImage;
    }

    public void setFaceImage(String faceImage) {
        this.faceImage = faceImage;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getFansCounts() {
        return fansCounts;
    }

    public void setFansCounts(Integer fansCounts) {
        this.fansCounts = fansCounts;
    }

    public Integer getFollowCounts() {
        return followCounts;
    }

    public void setFollowCounts(Integer followCounts) {
        this.followCounts = followCounts;
    }

    public Integer getReceiveLikeCounts() {
        return receiveLikeCounts;
    }

    public void setReceiveLikeCounts(Integer receiveLikeCounts) {
        this.receiveLikeCounts = receiveLikeCounts;
    }
}