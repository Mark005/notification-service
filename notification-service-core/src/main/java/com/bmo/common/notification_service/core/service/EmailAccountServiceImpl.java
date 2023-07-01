package com.bmo.common.notification_service.core.service;

import com.bmo.common.notification_service.core.dbmodel.EmailAccount;
import com.bmo.common.notification_service.core.event.AccountUpdatedEvent;
import com.bmo.common.notification_service.core.exception.EmailAlreadyExistsException;
import com.bmo.common.notification_service.core.exception.EntityNotFoundException;
import com.bmo.common.notification_service.core.mapper.EmailAccountMapper;
import com.bmo.common.notification_service.core.repository.EmailAccountRepository;
import com.bmo.common.notification_service.core.model.rest.EmailAccountCreateDto;
import com.bmo.common.notification_service.core.model.rest.EmailAccountUpdateDto;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailAccountServiceImpl implements EmailAccountService {

  private final ApplicationEventPublisher eventPublisher;

  private final EmailAccountRepository accountRepository;

  private final EmailAccountMapper emailAccountMapper;

  @Override
  public EmailAccount addEmailAccount(EmailAccountCreateDto newEmailAccount) {
    if (accountRepository.existsByEmail(newEmailAccount.getEmail())) {
      throw new EmailAlreadyExistsException(newEmailAccount.getEmail());
    }

    EmailAccount account = emailAccountMapper.mapToEntity(newEmailAccount);
    EmailAccount saved = accountRepository.save(account);
    eventPublisher.publishEvent(new AccountUpdatedEvent());
    return saved;
  }

  @Override
  public EmailAccount getEmailAccountById(UUID accountId) {
    return accountRepository.findById(accountId)
        .orElseThrow(() -> new EntityNotFoundException("EmailAccount", accountId));
  }

  @Override
  public List<EmailAccount> getAllEmailAccounts() {
    return accountRepository.findAll();
  }

  @Override
  public EmailAccount updateEmailAccount(UUID accountId, EmailAccountUpdateDto accountUpdateDto) {
    EmailAccount emailFromDb = accountRepository.findById(accountId)
        .orElseThrow(() -> new EntityNotFoundException("EmailAccount", accountId));

    EmailAccount merged = emailAccountMapper.merge(emailFromDb, accountUpdateDto);
    EmailAccount saved = accountRepository.save(merged);
    eventPublisher.publishEvent(new AccountUpdatedEvent());
    return saved;
  }

  @Override
  public void deleteEmailAccount(UUID accountId) {
    accountRepository.deleteById(accountId);
    eventPublisher.publishEvent(new AccountUpdatedEvent());
  }
}
