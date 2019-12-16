package com.atlantis.mapper;

import com.atlantis.domain.Users;
import com.atlantis.utils.MyMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UsersMapper extends MyMapper<Users> {
    /**
     * @Description: 用户收到赞/收藏数累加
     */
    @Update("update users set receive_like_counts=receive_like_counts+1 where id=#{userId}")
    public void addReceiveLikeCount(@Param("userId") String userId);

    /**
     * @Description: 用户收到赞/收藏数累减
     */
    @Update("update users set receive_like_counts=receive_like_counts-1 where id=#{userId}")
    public void reduceReceiveLikeCount(@Param("userId") String userId);

    /**
     * @Description: 增加粉丝数
     */
    @Update("update users set fans_counts=fans_counts+1 where id=#{userId}")
    public void addFansCount(@Param("userId") String userId);

    /**
     * @Description: 减少粉丝数
     */
    @Update("update users set fans_counts=fans_counts-1 where id=#{userId}")
    public void reduceFansCount(@Param("userId") String userId);

    /**
     * @Description: 增加关注数
     */
	@Update("update users set follow_counts=follow_counts+1 where id=#{userId}")
    public void addFollersCount(@Param("userId") String userId);

    /**
     * @Description: 减少关注数
     */
	@Update("update users set follow_counts=follow_counts-1 where id=#{userId}")
    public void reduceFollersCount(@Param("userId") String userId);
}