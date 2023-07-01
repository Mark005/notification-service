package com.bmo.common.notification_service.mapper;

import com.bmo.common.notification_service.configs.MapStructCommonConfig;
import com.bmo.common.notification_service.dbmodel.EmailAccount;
import com.bmo.common.notification_service.model.rest.EmailAccountCreateDto;
import com.bmo.common.notification_service.model.rest.EmailAccountResponseDto;
import com.bmo.common.notification_service.model.rest.EmailAccountUpdateDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapStructCommonConfig.class)
public interface EmailAccountMapper {

  @Mapping(target = "id", ignore = true)
  EmailAccount mapToEntity(EmailAccountCreateDto newEmailAccount);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "email", ignore = true)
  EmailAccount merge(@MappingTarget EmailAccount emailFromDb, EmailAccountUpdateDto accountUpdateDto);

  EmailAccountResponseDto mapToResponseDto(EmailAccount account);

  List<EmailAccountResponseDto> mapToResponseDto(List<EmailAccount> accounts);

}
