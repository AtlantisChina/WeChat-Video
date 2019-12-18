package com.atlantis.mapper;

import com.atlantis.domain.UsersReport;
import com.atlantis.domain.UsersReportExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UsersReportMapper {
    int countByExample(UsersReportExample example);

    int deleteByExample(UsersReportExample example);

    int deleteByPrimaryKey(String id);

    int insert(UsersReport record);

    int insertSelective(UsersReport record);

    List<UsersReport> selectByExample(UsersReportExample example);

    UsersReport selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") UsersReport record, @Param("example") UsersReportExample example);

    int updateByExample(@Param("record") UsersReport record, @Param("example") UsersReportExample example);

    int updateByPrimaryKeySelective(UsersReport record);

    int updateByPrimaryKey(UsersReport record);
}