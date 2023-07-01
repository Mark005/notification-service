package com.bmo.common.notification_service.controller;

import com.bmo.common.notification_service.dbmodel.EmailAccount;
import com.bmo.common.notification_service.mapper.EmailAccountMapper;
import com.bmo.common.notification_service.model.rest.EmailAccountCreateDto;
import com.bmo.common.notification_service.model.rest.EmailAccountResponseDto;
import com.bmo.common.notification_service.model.rest.EmailAccountUpdateDto;
import com.bmo.common.notification_service.service.EmailAccountService;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailAccountController {

  private final EmailAccountService emailAccountService;
  private final EmailAccountMapper emailAccountMapper;

  @PostMapping("/email-accounts")
  public ResponseEntity<EmailAccountResponseDto> addEmailAccount(
      @RequestBody @Valid EmailAccountCreateDto newEmailAccount) {

    EmailAccount emailAccount = emailAccountService.addEmailAccount(newEmailAccount);
    EmailAccountResponseDto responseDto = emailAccountMapper.mapToResponseDto(emailAccount);
    return ResponseEntity.ok(responseDto);
  }

  @GetMapping("/email-accounts/{id}")
  public ResponseEntity<EmailAccountResponseDto> getEmailAccountById(
      @NotNull @PathVariable("id") UUID accountId) {

    EmailAccount account = emailAccountService.getEmailAccountById(accountId);
    EmailAccountResponseDto responseDto = emailAccountMapper.mapToResponseDto(account);
    return ResponseEntity.ok(responseDto);
  }

  @GetMapping("/email-accounts")
  public ResponseEntity<List<EmailAccountResponseDto>> getAllEmailAccounts() {

    List<EmailAccount> accounts = emailAccountService.getAllEmailAccounts();
    List<EmailAccountResponseDto> responseDto = emailAccountMapper.mapToResponseDto(accounts);
    return ResponseEntity.ok(responseDto);
  }

  @PutMapping("/email-accounts/{id}")
  public ResponseEntity<EmailAccountResponseDto> updateEmailAccount(
      @NotNull @PathVariable("id") UUID accountId,
      @RequestBody @Valid EmailAccountUpdateDto accountUpdateDto) {

    EmailAccount emailAccount = emailAccountService.updateEmailAccount(accountId, accountUpdateDto);
    EmailAccountResponseDto responseDto = emailAccountMapper.mapToResponseDto(emailAccount);
    return ResponseEntity.ok(responseDto);
  }

  @DeleteMapping("/email-accounts/{id}")
  public ResponseEntity<Void> deleteEmailAccount(
      @NotNull @PathVariable("id") UUID accountId) {

    emailAccountService.deleteEmailAccount(accountId);
    return ResponseEntity.noContent().build();
  }

}
