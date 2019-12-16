package com.atlantis.mapper;

import com.atlantis.domain.Videos;
import com.atlantis.utils.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface VideosMapper extends MyMapper<Videos> {

}