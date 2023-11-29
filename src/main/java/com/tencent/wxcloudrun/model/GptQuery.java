package com.tencent.wxcloudrun.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class GptQuery implements Serializable {

  private Integer id;

  private String question;

  private String answer;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;
}
