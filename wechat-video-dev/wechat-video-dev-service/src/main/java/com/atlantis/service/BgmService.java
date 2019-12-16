package com.atlantis.service;

import com.atlantis.domain.Bgm;

import java.util.List;

/**
 * @Auther: Atlantis
 * @Date: 2019/12/3
 * @Description: 背景音乐服务层接口
 */
public interface BgmService {
    /**
     * @Description: 获取所有背景音乐
     */
    public List<Bgm> queryBgmList();

    /**
     * @Description: 根据id查询bgm信息
     */
    public Bgm queryBgmById(String bgmId);
}
