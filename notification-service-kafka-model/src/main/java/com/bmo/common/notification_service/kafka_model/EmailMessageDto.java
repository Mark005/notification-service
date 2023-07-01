package com.bmo.common.notification_service.kafka_model;

import java.util.List;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EmailMessageDto {

  private String from;

  private String subject;

  private String to;

  private EmailMessageContentDto message;

  private List<String> cc;

  private List<String> bcc;
}
