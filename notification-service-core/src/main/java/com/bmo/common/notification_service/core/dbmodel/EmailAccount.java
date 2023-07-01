package com.bmo.common.notification_service.dbmodel;


import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "email_account")
public class EmailAccount {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private UUID id;

  private String email;

  private String password;

  private String host;

  private Integer port;

  @Column(name = "connection_timeout")
  private Integer connectionTimeout;

  private Integer timeout;

  @Column(name = "fallback_priority")
  private Integer fallbackPriority;
}
