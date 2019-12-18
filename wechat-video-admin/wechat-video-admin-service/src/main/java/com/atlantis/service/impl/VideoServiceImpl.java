package com.atlantis.service.impl;

import com.atlantis.domain.Bgm;
import com.atlantis.domain.BgmExample;
import com.atlantis.domain.Videos;
import com.atlantis.domain.VideosExample;
import com.atlantis.domain.VideosExample.Criteria;
import com.atlantis.domain.vo.Reports;
import com.atlantis.mapper.BgmMapper;
import com.atlantis.mapper.UsersReportMapperCustom;
import com.atlantis.mapper.VideosMapper;
import com.atlantis.service.VideoService;
import com.atlantis.utils.ResultByPage;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: Atlantis
 * @Date: 2019/12/18
 * @Description: com.atlantis.service.impl
 * @version: 1.0
 */
@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    private VideosMapper videosMapper;

    @Autowired
    private BgmMapper bgmMapper;

    @Autowired
    private UsersReportMapperCustom usersReportMapperCustom;

    @Autowired
    private Sid sid;

    @Override
    public void addBgm(Bgm bgm) {
        bgm.setId(sid.nextShort());
        bgmMapper.insert(bgm);
    }

    @Override
    public ResultByPage queryBgmList(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);

        BgmExample example = new BgmExample();
        List<Bgm> list = bgmMapper.selectByExample(example);

        PageInfo<Bgm> pageList = new PageInfo<>(list);
        ResultByPage result = new ResultByPage();
        result.setTotal(pageList.getPages());
        result.setRows(list);
        result.setPage(page);
        result.setRecords(pageList.getTotal());
        return result;
    }

    @Override
    public void deleteBgm(String id) {
        bgmMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ResultByPage queryReportList(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);

        List<Reports> reportsList = usersReportMapperCustom.selectAllVideoReport();

        PageInfo<Reports> pageList = new PageInfo<Reports>(reportsList);
        ResultByPage grid = new ResultByPage();
        grid.setTotal(pageList.getPages());
        grid.setRows(reportsList);
        grid.setPage(page);
        grid.setRecords(pageList.getTotal());
        return grid;
    }

    @Override
    public void updateVideoStatus(String videoId, Integer status) {
        Videos video = new Videos();
        video.setId(videoId);
        video.setStatus(status);
        videosMapper.updateByPrimaryKeySelective(video);
    }

    @Override
    public ResultByPage queryVideos(Videos videos, Integer page, Integer pageSize) {
        String userId = "";
        String videoDesc = "";
        if (videos != null) {
            userId = videos.getUserId();
            videoDesc = videos.getVideoDesc();
        }
        PageHelper.startPage(page, pageSize);
        VideosExample videosExample = new VideosExample();
        Criteria videosCriteria = videosExample.createCriteria();
        if (StringUtils.isNotBlank(userId)) {
            videosCriteria.andUserIdLike("%" + userId + "%");
        }
        if (StringUtils.isNotBlank(videoDesc)) {
            videosCriteria.andVideoDescLike("%" + videoDesc + "%");
        }
        List<Videos> videosList = videosMapper.selectByExample(videosExample);
        PageInfo<Videos> pageList = new PageInfo<Videos>(videosList);
        ResultByPage grid = new ResultByPage();
        grid.setTotal(pageList.getPages());
        grid.setRows(videosList);
        grid.setPage(page);
        grid.setRecords(pageList.getTotal());
        return grid;
    }
}
