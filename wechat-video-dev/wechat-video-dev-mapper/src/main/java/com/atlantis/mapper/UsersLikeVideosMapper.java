package com.atlantis.mapper;

import com.atlantis.domain.UsersLikeVideos;
import com.atlantis.utils.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UsersLikeVideosMapper extends MyMapper<UsersLikeVideos> {
}