package com.atlantis.service;

import com.atlantis.domain.Bgm;
import com.atlantis.domain.Videos;
import com.atlantis.utils.ResultByPage;

/**
 * @Auther: Atlantis
 * @Date: 2019/12/18
 * @Description: com.atlantis.service
 * @version: 1.0
 */
public interface VideoService {
    public void addBgm(Bgm bgm);

    public ResultByPage queryBgmList(Integer page, Integer pageSize);

    public void deleteBgm(String id);

    public ResultByPage queryReportList(Integer page, Integer pageSize);

    public void updateVideoStatus(String videoId, Integer status);

    public ResultByPage queryVideos(Videos videos, Integer page, Integer pageSize);
}
