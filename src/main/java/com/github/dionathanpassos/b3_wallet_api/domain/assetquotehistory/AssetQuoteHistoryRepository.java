package com.github.dionathanpassos.b3_wallet_api.domain.assetquotehistory;

import com.github.dionathanpassos.b3_wallet_api.domain.asset.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface AssetQuoteHistoryRepository extends JpaRepository<AssetQuoteHistory, Long> {

    Optional<AssetQuoteHistory> findByAssetAndQuoteDate(Asset asset, LocalDate quoteDate);
}
