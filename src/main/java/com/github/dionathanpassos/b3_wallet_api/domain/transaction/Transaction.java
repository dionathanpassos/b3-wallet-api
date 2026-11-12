package com.github.dionathanpassos.b3_wallet_api.domain.transaction;

import com.github.dionathanpassos.b3_wallet_api.domain.account.Account;
import com.github.dionathanpassos.b3_wallet_api.domain.asset.Asset;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "tb_transactions")
@Getter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType type;

    @Column(nullable = false)
    private Long quantity;

    @Column(name = "unit_price", precision = 19, scale = 2, nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "total_amount", precision = 19, scale = 2, nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "transaction_date", nullable = false)
    private LocalDate transactionDate;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected Transaction() {}

    public static Transaction create(
            Account account,
            Asset asset,
            TransactionType type,
            Long quantity,
            BigDecimal unitPrice,
            BigDecimal totalAmount,
            LocalDate transactionDate
    ) {
        Transaction transaction = new Transaction();

        transaction.account = account;
        transaction.asset = asset;
        transaction.type = type;
        transaction.quantity = quantity;
        transaction.unitPrice = unitPrice;
        transaction.totalAmount = totalAmount;
        transaction.transactionDate = transactionDate;

        return transaction;
    }
}
