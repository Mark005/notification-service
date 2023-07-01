package com.bmo.common.notification_service.core.model.email;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
public class EmailMessage {

  private String subject;

  private String to;

  private EmailMessageContent message;

  private List<String> cc;

  private List<String> bcc;
}
