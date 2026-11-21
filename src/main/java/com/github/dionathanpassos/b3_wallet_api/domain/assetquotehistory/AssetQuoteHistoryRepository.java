package com.github.dionathanpassos.b3_wallet_api.domain.assetquotehistory;

import com.github.dionathanpassos.b3_wallet_api.domain.asset.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

public interface AssetQuoteHistoryRepository extends JpaRepository<AssetQuoteHistory, Long> {

    Optional<AssetQuoteHistory> findByAssetAndQuoteDate(Asset asset, LocalDate quoteDate);

    boolean existsByAssetAndQuoteDate(Asset asset, LocalDate quoteDate);

    @Query("SELECT q.quoteDate FROM AssetQuoteHistory q WHERE q.asset = :asset")
    Set<LocalDate> findExistingDatesByAsset(
            @Param("asset") Asset asset
    );
}
