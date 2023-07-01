package com.bmo.common.notification_service.core.service.email_sender;

import com.bmo.common.notification_service.core.event.AccountUpdatedEvent;
import com.bmo.common.notification_service.core.model.email.EmailMessage;
import com.bmo.common.notification_service.core.repository.EmailAccountRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {

  private final List<Map.Entry<String, Session>> emailToSession = new ArrayList<>();

  private final EmailAccountRepository accountRepository;

  @EventListener
  public void updateCachedSessionsAfterApplicationStart(ApplicationStartedEvent event) {
    updateSessions();
  }

  @EventListener
  public void updateCachedSessionsAfterAccountUpdating(AccountUpdatedEvent event) {
    updateSessions();
  }


  public void send(EmailMessage emailMessage) {
    for (Map.Entry<String, Session> stringSessionEntry : emailToSession) {
      String email = stringSessionEntry.getKey();
      Session session = stringSessionEntry.getValue();

      log.debug("Attempt to send email with email [{}]", email);

      try {
        Message message = new MimeMessage(session);

        message.setSubject(emailMessage.getSubject());
        message.setFrom(new InternetAddress(email));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailMessage.getTo()));
        message.setText(emailMessage.getMessage().getPlaintext());

        Transport.send(message);

        log.debug("Message has been sent with email [{}]", email);
        return;
      } catch (MessagingException e) {
        log.warn("Error occurred while sending email with email account [{}]", email, e);
      }
    }
    log.warn("Message has not been sent with any of available email accounts");
  }

  private void updateSessions() {
    log.debug("Session cache update initiated");
    emailToSession.clear();

    accountRepository.findAllActiveOrderedByPriority()
        .forEach(emailAccount -> {

          Properties props = new Properties();
          props.put("mail.smtp.host", "smtp.gmail.com");
          props.put("mail.smtp.port", "587");
          props.put("mail.smtp.auth", "true");
          props.put("mail.smtp.starttls.enable", "true");
          props.put("mail.smtp.connectiontimeout", "30000");
          props.put("mail.smtp.timeout", "60000");

          Session session = Session.getInstance(props,
              new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                  return new PasswordAuthentication(
                      emailAccount.getEmail(),
                      emailAccount.getPassword());
                }
              });

          emailToSession.add(Map.entry(emailAccount.getEmail(), session));
          log.debug("Session configured for email [{}]", emailAccount.getEmail());
        });
  }
}
