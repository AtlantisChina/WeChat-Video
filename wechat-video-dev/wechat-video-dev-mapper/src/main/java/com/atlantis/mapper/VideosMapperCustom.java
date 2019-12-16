package com.atlantis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.*;
import com.atlantis.domain.Videos;
import com.atlantis.domain.vo.VideosVO;
import com.atlantis.utils.MyMapper;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface VideosMapperCustom extends MyMapper<Videos> {
    /**
     * @Description: 条件查询所有视频列表
     */
    @Results(id = "BaseResultMap", value = {
            @Result(property = "id", column = "id", jdbcType = JdbcType.VARCHAR),
            @Result(property = "userId", column = "user_id", jdbcType = JdbcType.VARCHAR),
            @Result(property = "bgmId", column = "bgm_id", jdbcType = JdbcType.VARCHAR),
            @Result(property = "videoDesc", column = "video_desc", jdbcType = JdbcType.VARCHAR),
            @Result(property = "videoPath", column = "video_path", jdbcType = JdbcType.VARCHAR),
            @Result(property = "videoSeconds", column = "video_seconds", jdbcType = JdbcType.REAL),
            @Result(property = "videoWidth", column = "video_width", jdbcType = JdbcType.INTEGER),
            @Result(property = "videoHeight", column = "video_height", jdbcType = JdbcType.INTEGER),
            @Result(property = "coverPath", column = "cover_path", jdbcType = JdbcType.VARCHAR),
            @Result(property = "likeCounts", column = "like_counts", jdbcType = JdbcType.BIGINT),
            @Result(property = "status", column = "status", jdbcType = JdbcType.INTEGER),
            @Result(property = "createTime", column = "create_time", jdbcType = JdbcType.TIMESTAMP),
            @Result(property = "faceImage", column = "face_image", jdbcType = JdbcType.VARCHAR),
            @Result(property = "nickname", column = "nickname", jdbcType = JdbcType.VARCHAR)
    })
    @Select("<script>"
            + "select v.*,u.face_image as face_image,u.nickname as nickname from videos v "
            + "left join users u on u.id = v.user_id "
            + "where 1 = 1"
            + " <if test='searchText != null'>"
            + "     and concat(v.video_desc,u.nickname,u.id) like '%${searchText}%' "
            + " </if>"
            + "and v.status = 1 "
            + "order by v.create_time desc "
            + "</script>")
    public List<VideosVO> queryAllVideos(@Param("searchText") String searchText);

    /**
     * @Description: 查询关注的视频
     */
    @ResultMap("BaseResultMap")
    @Select("select v.*,u.face_image as face_image,u.nickname as nickname from videos v "
            + "left join users u on v.user_id = u.id "
            + "where "
            + "     v.user_id in (select uf.user_id from users_fans uf where uf.fan_id = #{userId}) "
            + "     and v.status = 1 "
            + "order by v.create_time desc")
    public List<VideosVO> queryMyFollowVideos(@Param("userId") String userId);

    /**
     * @Description: 查询点赞视频
     */
    @ResultMap("BaseResultMap")
    @Select("select v.*,u.face_image as face_image,u.nickname as nickname from videos v "
            + "left join users u on v.user_id = u.id "
            + "where "
            + "     v.id in (select ulv.video_id from users_like_videos ulv where ulv.user_id = #{userId}) "
            + "     and v.status = 1 "
            + "order by v.create_time desc")
    public List<VideosVO> queryMyLikeVideos(@Param("userId") String userId);

    /**
     * @Description: 对视频喜欢的数量进行累加
     */
    @Update("update videos set like_counts=like_counts+1 where id=#{videoId}")
    public void addVideoLikeCount(@Param("videoId") String videoId);

    /**
     * @Description: 对视频喜欢的数量进行累减
     */
    @Update("update videos set like_counts=like_counts-1 where id=#{videoId}")
    public void reduceVideoLikeCount(@Param("videoId") String videoId);
}