package com.tencent.wxcloudrun.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.tencent.wxcloudrun.service.GptService;
import com.tencent.wxcloudrun.util.JsonBuilder;
import com.tencent.wxcloudrun.util.RestUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;


@Service
public class GptServiceImpl implements GptService {


  public GptServiceImpl() {

  }

  @Override
  public String chat(String question) {
    JSONObject json = JsonBuilder.forObject()
            .with("question", question).toJsonObject();

    JSONObject result = RestUtil.postJson("http://49.51.38.186:5000/gpt", json,setHeader());
    return result.getString("result");
  }

  public HttpHeaders setHeader() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");
    return headers;
  }
}
