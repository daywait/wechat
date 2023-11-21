package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.service.GptService;
import org.springframework.stereotype.Service;


@Service
public class GptServiceImpl implements GptService {


  public GptServiceImpl() {

  }

  @Override
  public String chat(String question) {
    return "test";
  }
}
