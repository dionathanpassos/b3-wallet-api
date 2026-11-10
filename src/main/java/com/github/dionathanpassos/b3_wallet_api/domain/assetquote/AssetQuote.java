package com.github.dionathanpassos.b3_wallet_api.domain.assetquote;

import com.github.dionathanpassos.b3_wallet_api.domain.asset.Asset;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.Getter;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "tb_asset_quotes")
@Getter
public class AssetQuote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "asset_id", nullable = false, unique = true)
    Asset asset;

    @Column(precision = 19, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(name = "price_change", precision = 19, scale = 2)
    private BigDecimal priceChange;

    @Column(name = "change_percent", precision = 7, scale = 4)
    @Digits(integer = 3, fraction = 4)
    private BigDecimal changePercent;

    @Column(name = "day_high", precision = 19, scale = 2)
    private BigDecimal dayHigh;

    @Column(name = "day_low", precision = 19, scale = 2)
    private BigDecimal dayLow;

    @Column(name = "previous_close", precision = 19, scale = 2)
    private BigDecimal previousClose;

    @Column(nullable = false)
    private Long volume;

    @Column(name = "quote_time", nullable = false)
    private Instant quoteTime;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    private AssetQuote() {}

    public static AssetQuote create(
            Asset asset,
            BigDecimal price,
            BigDecimal priceChange,
            BigDecimal changePercent,
            BigDecimal dayHigh,
            BigDecimal dayLow,
            BigDecimal previousClose,
            Long volume,
            Instant quoteTime
    ) {
        AssetQuote assetQuote = new AssetQuote();

        assetQuote.asset = asset;
        assetQuote.price = price;
        assetQuote.priceChange = priceChange;
        assetQuote.changePercent = changePercent;
        assetQuote.dayHigh = dayHigh;
        assetQuote.dayLow = dayLow;
        assetQuote.previousClose = previousClose;
        assetQuote.volume = volume;
        assetQuote.quoteTime = quoteTime;

        return assetQuote;
    }

    public void updateQuote(
            BigDecimal price,
            BigDecimal priceChange,
            BigDecimal changePercent,
            BigDecimal dayHigh,
            BigDecimal dayLow,
            BigDecimal previousClose,
            Long volume,
            Instant quoteTime
    ) {
        this.price = price;
        this.priceChange = priceChange;
        this.changePercent = changePercent;
        this.dayHigh = dayHigh;
        this.dayLow = dayLow;
        this.previousClose = previousClose;
        this.volume = volume;
        this.quoteTime = quoteTime;
    }
}
