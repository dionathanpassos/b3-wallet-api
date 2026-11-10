package com.github.dionathanpassos.b3_wallet_api.domain.asset;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "tb_assets")
@Getter
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ticker;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssetType type;

    @Column(nullable = false)
    private String exchange;

    @Column(nullable = false)
    private String currency;

    private String sector;

    @Column(name = "sub_sector")
    private String subSector;

    @Column(nullable = false)
    private boolean active;

    @Column(name = "logo_url")
    private String logoUrl;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    protected Asset() {}

    public static Asset createAsset(
            String ticker,
            String name,
            AssetType type,
            String exchange,
            String currency,
            String sector,
            String subSector,
            boolean active,
            String logoUrl
    ) {
        Asset asset = new Asset();

        asset.ticker = ticker;
        asset.name = name;
        asset.type = type;
        asset.exchange = exchange;
        asset.currency = currency;
        asset.sector = sector;
        asset.subSector = subSector;
        asset.active = active;
        asset.logoUrl = logoUrl;

        return asset;
    };

    public void updateAsset(
            String name,
            AssetType type,
            String exchange,
            String currency,
            String sector,
            String subSector,
            boolean active,
            String logoUrl
    ) {
        this.name = name;
        this.type = type;
        this.exchange = exchange;
        this.currency = currency;
        this.sector = sector;
        this.subSector = subSector;
        this.active = active;
        this.logoUrl = logoUrl;
    }
}
