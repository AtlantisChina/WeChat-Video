package com.atlantis.mapper;

import com.atlantis.domain.Bgm;
import com.atlantis.utils.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface BgmMapper extends MyMapper<Bgm> {
}