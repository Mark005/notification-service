package com.bmo.common.notification_service.core.model.email;

import java.util.List;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EmailMessage {

  private String from;

  private String subject;

  private String to;

  private EmailMessageContent message;

  private List<String> cc;

  private List<String> bcc;
}
