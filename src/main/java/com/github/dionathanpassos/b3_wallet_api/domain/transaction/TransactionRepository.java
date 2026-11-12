package com.github.dionathanpassos.b3_wallet_api.domain.transaction;

import com.github.dionathanpassos.b3_wallet_api.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t " +
            "JOIN t.account a " +
            "WHERE a.user = :user " +
            "AND (:type IS NULL OR t.type = :type) " +
            "AND (:accountId IS NULL OR t.account.id = :accountId) " +
            "AND (:assetId IS NULL OR t.asset.id = :assetId) " +
            "AND (:startDate IS NULL OR t.transactionDate >= :startDate) " +
            "AND (:endDate IS NULL OR t.transactionDate <= :endDate) ")
    Page<Transaction> findAllFiltered(
            @Param("search") String search,
            @Param("accountId") Long accountId,
            @Param("assetId") Long assetId,
            @Param("type") TransactionType type,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("user") User user,
            Pageable pageable
    );
}
