package com.github.dionathanpassos.b3_wallet_api.domain.asset;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Long> {

    Optional<Asset> findByTicker(String ticker);
}
