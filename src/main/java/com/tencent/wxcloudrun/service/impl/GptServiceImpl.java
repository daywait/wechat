package com.tencent.wxcloudrun.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.tencent.wxcloudrun.dao.GptQueryMapper;
import com.tencent.wxcloudrun.model.GptQuery;
import com.tencent.wxcloudrun.service.GptService;
import com.tencent.wxcloudrun.util.JsonBuilder;
import com.tencent.wxcloudrun.util.RestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;


@Service
public class GptServiceImpl implements GptService {


  @Autowired
  GptQueryMapper mapper;

  public GptServiceImpl() {

  }

  @Override
  public Integer insertQuestion(String question) {
    GptQuery query = GptQuery.builder()
            .question(question)
            .build();
    return mapper.insertQuery(query);
  }

  @Async
  @Override
  public void asyncChat(Integer id, String question) {
    JSONObject json = JsonBuilder.forObject()
            .with("question", question).toJsonObject();
    JSONObject result = RestUtil.postJson("http://49.51.38.186:5000/gpt", json,setHeader());
    GptQuery query = GptQuery.builder()
            .id(id)
            .answer(result.getString("result"))
            .build();
    mapper.updateQuery(query);

  }

  @Override
  public String getResult(Integer id) {
    GptQuery query = mapper.getQuery(id);
    if (query.getAnswer() != null) {
      return query.getAnswer();
    }
    return "";
  }

  public HttpHeaders setHeader() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");
    return headers;
  }
}
