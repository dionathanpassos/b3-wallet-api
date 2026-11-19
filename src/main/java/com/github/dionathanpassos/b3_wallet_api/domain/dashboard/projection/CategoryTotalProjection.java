package com.github.dionathanpassos.b3_wallet_api.domain.dashboard.projection;

import com.github.dionathanpassos.b3_wallet_api.domain.asset.AssetType;

import java.math.BigDecimal;

public interface CategoryTotalProjection {
    AssetType getCategory();
    BigDecimal getAmount();
}
