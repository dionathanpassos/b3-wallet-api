package com.github.dionathanpassos.b3_wallet_api.domain.assetquotehistory;

import com.github.dionathanpassos.b3_wallet_api.domain.asset.Asset;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "tb_asset_quote_history",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_asset_quote_history_asset_date",
                        columnNames = {"asset_id", "quote_date"}
                )
        })
@Getter
public class AssetQuoteHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "quote_date", nullable = false)
    private LocalDate quoteDate;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    private AssetQuoteHistory() {}

    public static AssetQuoteHistory create(
            Asset asset,
            BigDecimal price,
            LocalDate quoteDate
    ) {
        AssetQuoteHistory history = new AssetQuoteHistory();

        history.asset = asset;
        history.price = price;
        history.quoteDate = quoteDate;

        return history;
    }
}
