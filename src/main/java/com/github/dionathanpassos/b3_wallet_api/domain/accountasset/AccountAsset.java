package com.github.dionathanpassos.b3_wallet_api.domain.accountasset;

import com.github.dionathanpassos.b3_wallet_api.domain.account.Account;
import com.github.dionathanpassos.b3_wallet_api.domain.asset.Asset;
import com.github.dionathanpassos.b3_wallet_api.domain.transaction.TransactionType;
import com.github.dionathanpassos.b3_wallet_api.exception.BusinessException;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;

@Entity
@Table(name = "tb_account_assets")
@Getter
public class AccountAsset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    @Column(nullable = false)
    private Long quantity;

    @Column(name = "average_price", nullable = false)
    private BigDecimal averagePrice;

    @Column(name = "total_invested", nullable = false)
    private BigDecimal totalInvested;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    private AccountAsset() {}

    public static AccountAsset create(Account account, Asset asset) {
        AccountAsset accountAsset = new AccountAsset();

        accountAsset.account = account;
        accountAsset.asset = asset;
        accountAsset.quantity = 0L;
        accountAsset.averagePrice = BigDecimal.ZERO;
        accountAsset.totalInvested = BigDecimal.ZERO;

        return accountAsset;
    }

    public void applyPosition(
            TransactionType type,
            Long quantity,
            BigDecimal totalAmount
    ) {
        if (type == TransactionType.BUYING) {
            applyBuy(totalAmount, quantity);
        } else if (type == TransactionType.SELLING) {
            applySell(quantity);
        }

    }
    public void applyBuy(
            BigDecimal totalAmount,
            Long quantity
    ) {
        BigDecimal newTotalInvested = this.totalInvested.add(totalAmount);
        Long newQuantity = this.quantity + quantity;

        this.averagePrice = newTotalInvested.divide(BigDecimal.valueOf(newQuantity), 2, RoundingMode.HALF_UP);

        this.quantity = newQuantity;
        this.totalInvested = newTotalInvested;

    }
    public void applySell(
            Long quantity
    ) {
        if (this.quantity.compareTo(quantity) < 0) {
            throw new BusinessException("Quantidade insuficiente para venda");
        }

        Long newQuantity = this.quantity - quantity;
        BigDecimal newTotalInvested = this.totalInvested.subtract(this.averagePrice.multiply(BigDecimal.valueOf(quantity)));

        this.quantity = newQuantity;
        this.totalInvested = newTotalInvested;

        if (newQuantity == 0) {
            this.averagePrice = BigDecimal.ZERO;
            this.totalInvested = BigDecimal.ZERO;
        }

    }


}
