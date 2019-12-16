package com.atlantis.mapper;

import java.util.List;

import com.atlantis.domain.SearchRecords;
import com.atlantis.utils.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface SearchRecordsMapper extends MyMapper<SearchRecords> {
    /**
     * @Description: 获取热搜词
     */
    @Select("SELECT content FROM search_records GROUP BY content ORDER BY COUNT(content) DESC")
    public List<String> getHotWords();
}