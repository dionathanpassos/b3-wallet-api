package com.github.dionathanpassos.b3_wallet_api.domain.transaction;

import com.github.dionathanpassos.b3_wallet_api.domain.transaction.dto.TransactionResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(target = "accountId", source = "account.id")
    @Mapping(target = "assetId", source = "asset.id")
    @Mapping(target = "brokerId", source = "broker.id")
    TransactionResponseDTO fromEntity(Transaction transaction);
}
