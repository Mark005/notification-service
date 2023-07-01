package com.bmo.common.notification_service.core.service;

import com.bmo.common.notification_service.core.dbmodel.EmailAccount;
import com.bmo.common.notification_service.core.model.rest.EmailAccountCreateDto;
import com.bmo.common.notification_service.core.model.rest.EmailAccountUpdateDto;
import java.util.List;
import java.util.UUID;

public interface EmailAccountService {

  EmailAccount addEmailAccount(EmailAccountCreateDto newEmailAccount);

  EmailAccount getEmailAccountById(UUID accountId);

  List<EmailAccount> getAllEmailAccounts();

  EmailAccount updateEmailAccount(UUID accountId, EmailAccountUpdateDto accountUpdateDto);

  void deleteEmailAccount(UUID accountId);
}
