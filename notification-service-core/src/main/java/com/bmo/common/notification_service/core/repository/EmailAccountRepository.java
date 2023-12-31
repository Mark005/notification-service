package com.bmo.common.notification_service.core.repository;

import com.bmo.common.notification_service.core.dbmodel.EmailAccount;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmailAccountRepository extends JpaRepository<EmailAccount, UUID> {

  boolean existsByEmail(String email);

  @Query("""
      from EmailAccount 
      where fallbackPriority > 0 
      order by fallbackPriority desc""")
  List<EmailAccount> findAllActiveOrderedByPriority();
}