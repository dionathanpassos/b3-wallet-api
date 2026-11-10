package com.github.dionathanpassos.b3_wallet_api.domain.account;

import com.github.dionathanpassos.b3_wallet_api.domain.account.dto.AccountResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {


    AccountResponseDTO fromEntity(Account account);
}
