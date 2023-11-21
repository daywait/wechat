package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.ChatRequest;
import com.tencent.wxcloudrun.dto.CounterRequest;
import com.tencent.wxcloudrun.model.Counter;
import com.tencent.wxcloudrun.service.CounterService;
import com.tencent.wxcloudrun.service.GptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * gpt接口
 */
@RestController

public class GptController {

  final GptService gptService;
  final Logger logger;

  public GptController(@Autowired GptService gptService) {
    this.gptService = gptService;
    this.logger = LoggerFactory.getLogger(GptController.class);
  }


  /**
   * gpt chat
   * @return API response json
   */
  @PostMapping(value = "/gpt/chat")
  ApiResponse chat(@RequestBody ChatRequest req) {
    logger.info("/gpt/chat get request");
    String answer = gptService.chat(req.getQuestion());

    return ApiResponse.ok(answer);
  }
  
}