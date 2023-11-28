package com.tencent.wxcloudrun.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.tencent.wxcloudrun.service.GptService;
import com.tencent.wxcloudrun.util.JsonBuilder;
import com.tencent.wxcloudrun.util.RestUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;


@Service
public class GptServiceImpl implements GptService {


  public GptServiceImpl() {

  }

  @Override
  public String chat(String question) {
    JSONObject params = JsonBuilder.forObject()
            .with("question", question).toJsonObject();
    JSONObject result = RestUtil.get("http://49.51.38.186:5000/gpt", params);
    return result.getString("answer");
  }
}
