package com.github.dionathanpassos.b3_wallet_api.domain.broker;

import com.github.dionathanpassos.b3_wallet_api.domain.broker.dto.BrokerResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrokerMapper {


    BrokerResponseDTO fromEntity(Broker broker);
}
