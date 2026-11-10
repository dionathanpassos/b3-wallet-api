package com.github.dionathanpassos.b3_wallet_api.domain.assetquotes;

import com.github.dionathanpassos.b3_wallet_api.domain.asset.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssetQuoteRepository extends JpaRepository<AssetQuote, Long> {

    Optional<AssetQuote> findByAsset(Asset asset);
}
