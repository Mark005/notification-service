package com.bmo.common.notification_service.core.model.email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class EmailMessageContent {

  private String plaintext;
}
