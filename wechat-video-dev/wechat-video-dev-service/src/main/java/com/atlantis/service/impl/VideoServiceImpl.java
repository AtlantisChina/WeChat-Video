package com.atlantis.service.impl;

import com.atlantis.domain.Comments;
import com.atlantis.domain.SearchRecords;
import com.atlantis.domain.UsersLikeVideos;
import com.atlantis.domain.Videos;
import com.atlantis.domain.vo.CommentsVO;
import com.atlantis.domain.vo.VideosVO;
import com.atlantis.mapper.*;
import com.atlantis.service.VideoService;
import com.atlantis.utils.ResultByPage;
import com.atlantis.utils.TimeAgoUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.Date;
import java.util.List;

/**
 * @Auther: Atlantis
 * @Date: 2019/12/3
 * @Description: 视频服务层接口实现类
 * @version: 1.0
 */
@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    private Sid sid;

    @Autowired
    private VideosMapper videosMapper;

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private VideosMapperCustom videosMapperCustom;

    @Autowired
    private SearchRecordsMapper searchRecordsMapper;

    @Autowired
    private UsersLikeVideosMapper usersLikeVideosMapper;

    @Autowired
    private CommentsMapper commentMapper;

    @Autowired
    private CommentsMapperCustom commentMapperCustom;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String saveVideo(Videos video) {
        String id = sid.nextShort();
        video.setId(id);
        videosMapper.insertSelective(video);
        return id;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateVideo(String videoId, String coverPath) {
        Videos video = new Videos();
        video.setId(videoId);
        video.setCoverPath(coverPath);
        videosMapper.updateByPrimaryKeySelective(video);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryVideoIDIsExist(String videoId) {
        Videos video = new Videos();
        video.setId(videoId);
        if (videosMapper.selectOne(video) != null) {
            // 存在此视频
            return true;
        }
        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ResultByPage getAllVideos(String searchText, Integer isSaveRecord, Integer page, Integer pageSize) {
        // 保存热搜词记录
        if (StringUtils.isBlank(searchText)) {
            // 如果为空字符串则转化成null
            searchText = null;
        }
        if (isSaveRecord != null && isSaveRecord == 1) {
            SearchRecords records = new SearchRecords();
            records.setId(sid.nextShort());
            records.setContent(searchText);
            searchRecordsMapper.insert(records);
        }

        // 查询和分页视频信息
        PageHelper.startPage(page, pageSize);
        List<VideosVO> list = videosMapperCustom.queryAllVideos(searchText);
        PageInfo<VideosVO> pageList = new PageInfo<>(list);
        ResultByPage resultByPage = new ResultByPage();
        resultByPage.setPage(page);
        resultByPage.setTotal(pageList.getPages());
        resultByPage.setRows(list);
        resultByPage.setRecords(pageList.getTotal());
        return resultByPage;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<String> getHotWords() {
        return searchRecordsMapper.getHotWords();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ResultByPage queryMyLikeVideos(String userId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<VideosVO> list = videosMapperCustom.queryMyLikeVideos(userId);

        PageInfo<VideosVO> pageList = new PageInfo<>(list);
        ResultByPage resultByPage = new ResultByPage();
        resultByPage.setPage(page);
        resultByPage.setTotal(pageList.getPages());
        resultByPage.setRows(list);
        resultByPage.setRecords(pageList.getTotal());
        return resultByPage;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ResultByPage queryMyFollowVideos(String userId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<VideosVO> list = videosMapperCustom.queryMyFollowVideos(userId);

        PageInfo<VideosVO> pageList = new PageInfo<>(list);
        ResultByPage resultByPage = new ResultByPage();
        resultByPage.setPage(page);
        resultByPage.setTotal(pageList.getPages());
        resultByPage.setRows(list);
        resultByPage.setRecords(pageList.getTotal());
        return resultByPage;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void userLikeVideo(String userId, String videoId, String videoCreaterId) {
        // 保存用户和视频的喜欢点赞关联关系表
        UsersLikeVideos ulv = new UsersLikeVideos();
        ulv.setId(sid.nextShort());
        ulv.setUserId(userId);
        ulv.setVideoId(videoId);
        usersLikeVideosMapper.insert(ulv);

        // 视频喜欢数量累加
        videosMapperCustom.addVideoLikeCount(videoId);
        // 用户受喜欢数量的累加
        usersMapper.addReceiveLikeCount(videoCreaterId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void userUnLikeVideo(String userId, String videoId, String videoCreaterId) {
        // 删除用户和视频的喜欢点赞关联关系表
        Example example = new Example(UsersLikeVideos.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("videoId", videoId);
        usersLikeVideosMapper.deleteByExample(example);

        // 视频喜欢数量累减
        videosMapperCustom.reduceVideoLikeCount(videoId);
        // 用户受喜欢数量的累减
        usersMapper.reduceReceiveLikeCount(videoCreaterId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveComment(Comments comment) {
        comment.setId(sid.nextShort());
        comment.setCreateTime(new Date());
        commentMapper.insert(comment);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ResultByPage getAllComments(String videoId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<CommentsVO> list = commentMapperCustom.queryComments(videoId);
        for (CommentsVO c : list) {
            // 根据留言时间添加时间戳
            String timeAgo = TimeAgoUtils.format(c.getCreateTime());
            c.setTimeAgoStr(timeAgo);
        }

        PageInfo<CommentsVO> pageList = new PageInfo<>(list);
        ResultByPage resultByPage = new ResultByPage();
        resultByPage.setTotal(pageList.getPages());
        resultByPage.setRows(list);
        resultByPage.setPage(page);
        resultByPage.setRecords(pageList.getTotal());
        return resultByPage;
    }
}