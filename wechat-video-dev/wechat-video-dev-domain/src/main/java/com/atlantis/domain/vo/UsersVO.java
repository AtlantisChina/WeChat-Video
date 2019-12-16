package com.atlantis.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UsersVO {
    private String id;
    private String username; // 用户名
    @JsonIgnore // 安全性，返回的json中不显示密码
    private String password; // 密码
    private String faceImage; // 头像，如果没有默认给一张
    private String nickname; // 用户昵称
    private Integer fansCounts; // 粉丝数量
    private Integer followCounts; // 关注总数
    private Integer receiveLikeCounts; // 收到赞/收藏的数量

    private String userToken; // 用户Token
    private boolean isFollow; //查询到的用户是否被访问者关注

    public UsersVO() {
        super();
    }

    public UsersVO(String id, String username, String password, String faceImage, String nickname, Integer fansCounts,
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

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public boolean isFollow() {
        return isFollow;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
    }
}