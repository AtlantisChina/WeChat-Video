package com.atlantis.service;

import com.atlantis.domain.Comments;
import com.atlantis.domain.Videos;
import com.atlantis.utils.ResultByPage;

import java.util.List;

/**
 * @Auther: Atlantis
 * @Date: 2019/12/8
 * @Description: 视频服务层接口
 * @version: 1.0
 */
public interface VideoService {
    /**
     * @Description: 保存视频
     */
    public String saveVideo(Videos video);

    /**
     * @Description: 修改视频的封面
     */
    public void updateVideo(String videoId, String coverPath);

    /**
     * @Description: 根据ID判断视频是否存在
     */
    public boolean queryVideoIDIsExist(String videoId);

    /**
     * @Description: 分页查询视频列表
     */
    public ResultByPage getAllVideos(String searchText, Integer isSaveRecord, Integer page, Integer pageSize);

    /**
     * @Description: 获取热搜词
     */
    public List<String> getHotWords();

    /**
     * @Description: 查询我喜欢的视频列表
     */
    public ResultByPage queryMyLikeVideos(String userId, Integer page, Integer pageSize);

    /**
     * @Description: 查询我关注的人的视频列表
     */
    public ResultByPage queryMyFollowVideos(String userId, Integer page, Integer pageSize);

    /**
     * @Description: 用户喜欢/点赞视频
     */
    public void userLikeVideo(String userId, String videoId, String videoCreaterId);

    /**
     * @Description: 用户不喜欢/取消点赞视频
     */
    public void userUnLikeVideo(String userId, String videoId, String videoCreaterId);

    /**
     * @Description: 用户留言
     */
    public void saveComment(Comments comment);

    /**
     * @Description: 留言分页
     */
    public ResultByPage getAllComments(String videoId, Integer page, Integer pageSize);
}
