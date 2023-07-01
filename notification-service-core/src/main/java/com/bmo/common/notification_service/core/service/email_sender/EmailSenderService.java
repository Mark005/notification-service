package com.bmo.common.notification_service.core.service.email_sender;

import com.bmo.common.notification_service.core.model.email.EmailMessage;

public interface EmailSenderService {

  void send(EmailMessage emailMessage);
}
