package com.atlantis.mapper;

import java.util.List;

import com.atlantis.domain.Comments;
import com.atlantis.utils.MyMapper;
import com.atlantis.domain.vo.CommentsVO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface CommentsMapperCustom extends MyMapper<Comments> {
    /**
     * @Description: 获取视频的留言
     */
    @Results(id = "BaseResultMap", value = {
            @Result(property = "id", column = "id", jdbcType = JdbcType.VARCHAR),
            @Result(property = "videoId", column = "video_id", jdbcType = JdbcType.VARCHAR),
            @Result(property = "fromUserId", column = "from_user_id", jdbcType = JdbcType.VARCHAR),
            @Result(property = "createTime", column = "create_time", jdbcType = JdbcType.TIMESTAMP),
            @Result(property = "comment", column = "comment", jdbcType = JdbcType.LONGVARCHAR),
            @Result(property = "faceImage", column = "face_image", jdbcType = JdbcType.VARCHAR),
            @Result(property = "nickname", column = "nickname", jdbcType = JdbcType.VARCHAR),
            @Result(property = "toNickname", column = "toNickname", jdbcType = JdbcType.VARCHAR)
    })
    @Select("select c.*,u.face_image as face_image,u.nickname,tu.nickname as toNickname "
            + "from comments c "
            + "left join users u on c.from_user_id = u.id "
            + "left join users tu on c.to_user_id = tu.id "
            + "where c.video_id = #{videoId} order by c.create_time desc ")
    public List<CommentsVO> queryComments(@Param("videoId") String videoId);
}