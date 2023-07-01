package com.bmo.common.notification_service.exception;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ExceptionResponseBody {

  private Integer status;
  private String error;
  private String message;
  private LocalDateTime timestamp;
}
