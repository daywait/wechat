package com.tencent.wxcloudrun.service;


public interface GptService {

  Integer insertQuestion(String question);
  void asyncChat(Integer id, String question);
  String getResult(Integer id);
}
