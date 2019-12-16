package com.atlantis.service;

import com.atlantis.domain.Users;
import com.atlantis.domain.UsersReport;

/**
 * @Auther: Atlantis
 * @Date: 2019/12/3
 * @Description: 用户服务层接口
 */
public interface UserService {
    /**
     * @Description: 判断用户名是否存在
     */
    public boolean queryUsernameIsExist(String username);

    /**
     * @Description: 判断用户ID是否存在
     */
    public boolean queryUserIDIsExist(String userId);

    /**
     * @Description: 保存用户（用户注册功能）
     */
    public void saveUser(Users user);

    /**
     * @Description: 用户登录：根据用户名和密码查询用户
     */
    public Users queryUserForLogin(String username, String password);

    /**
     * @Description: 更新用户信息
     */
    public void updateUserInfo(Users user);

    /**
     * @Description: 根据用户id查询用户信息
     */
    public Users queryUserInfo(String userId);

    /**
     * @Description: 查询用户是否喜欢点赞视频
     */
    public boolean isUserLikeVideo(String userId, String videoId);

    /**
     * @Description: 增加用户和粉丝的关系
     */
    public void saveUserFanRelation(String userId, String fanId);

    /**
     * @Description: 删除用户和粉丝的关系
     */
    public void deleteUserFanRelation(String userId, String fanId);

    /**
     * @Description: 查询用户是否关注
     */
    public boolean queryIfFollow(String userId, String fanId);

    /**
     * @Description: 举报用户
     */
    public void reportUser(UsersReport userReport);
}
