package com.github.dionathanpassos.b3_wallet_api.domain.position;

import com.github.dionathanpassos.b3_wallet_api.domain.account.Account;
import com.github.dionathanpassos.b3_wallet_api.domain.asset.Asset;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "account_assets")
@Getter
public class Position {

    private Long id;
    private Account account;
    private Asset asset;
    private Long quantity;
    private BigDecimal averagePrice;
    private BigDecimal totalInvested;
    private Instant createdAt;
    private Instant updatedAt;
}
