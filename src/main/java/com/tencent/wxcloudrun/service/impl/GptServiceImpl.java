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
  public String chat(String question) {
    asyncGetGpt(question);
    return "ok";
  }

  @Async
  public void asyncGetGpt(String question) {
    JSONObject json = JsonBuilder.forObject()
            .with("question", question).toJsonObject();
    GptQuery query = GptQuery.builder()
            .question(question)
            .build();
    mapper.insertQuery(query);
    JSONObject result = RestUtil.postJson("http://49.51.38.186:5000/gpt", json,setHeader());
    query.setAnswer(result.getString("result"));
    mapper.updateQuery(query);

  }

  public HttpHeaders setHeader() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");
    return headers;
  }
}
