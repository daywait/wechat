package com.tencent.wxcloudrun.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.service.GptService;
import com.tencent.wxcloudrun.util.RestUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;


@Service
public class GptServiceImpl implements GptService {


  public GptServiceImpl() {

  }

  @Override
  public String chat(String question) {
    JSONObject result = RestUtil.get("http://49.51.38.186:5111", JSONObject.parseObject(question));
    return result.toString();
  }

  public HttpHeaders setHeader() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");
    return headers;
  }
}
