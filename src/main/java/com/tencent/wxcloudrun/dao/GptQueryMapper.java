package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.GptQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GptQueryMapper {

  int insertQuery(GptQuery query);

  void updateQuery(GptQuery query);

  GptQuery getQuery(@Param("id") Integer id);

  void removeQuery(@Param("id") Integer id);
}
