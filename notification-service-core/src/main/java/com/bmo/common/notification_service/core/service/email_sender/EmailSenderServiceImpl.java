package com.bmo.common.notification_service.core.service;

import com.bmo.common.notification_service.core.event.AccountUpdatedEvent;
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
  public void updateCachedSessions(ApplicationStartedEvent event) {
    updateSessions();
  }

  @EventListener
  public void updateCachedSessions(AccountUpdatedEvent event) {
    updateSessions();
  }


  public void send(String test) {
    for (Map.Entry<String, Session> stringSessionEntry : emailToSession) {
      String email = stringSessionEntry.getKey();
      Session session = stringSessionEntry.getValue();

      log.debug("Attempt to send email with email [{}]", email);

      final String target = "likethewind322@gmail.com";

      try {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(email));
        message.setRecipients(
            Message.RecipientType.TO,
            InternetAddress.parse(target)
        );
        message.setSubject("Testing Gmail TLS");
        message.setText("Dear Mail Crawler,"
            + "\n\n Please do not spam my email!");

        Transport.send(message);

        System.out.println("Done");
        break;
      } catch (MessagingException e) {
        log.warn("Error occurred while sending the message with email [{}]", email, e);
      }
    }
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
