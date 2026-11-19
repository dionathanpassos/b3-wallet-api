package com.github.dionathanpassos.b3_wallet_api.domain.accountasset;

import com.github.dionathanpassos.b3_wallet_api.domain.account.Account;
import com.github.dionathanpassos.b3_wallet_api.domain.asset.Asset;
import com.github.dionathanpassos.b3_wallet_api.domain.dashboard.projection.CategoryTotalProjection;
import com.github.dionathanpassos.b3_wallet_api.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountAssetRepository extends JpaRepository<AccountAsset, Long> {


    Optional<AccountAsset> findByAccountAndAssetAndAccountUserId(Account account, Asset asset, Long userId);

    @Query("SELECT DISTINCT aa.asset FROM AccountAsset aa WHERE aa.quantity > 0")
    List<Asset> findDistinctAssetsWithActivePositions();

    List<AccountAsset> findAllByAccountUser(User user);

    @Query("SELECT a.type AS category, SUM(aa.quantity * aq.price) AS amount " +
            "FROM AccountAsset aa " +
            "JOIN aa.asset a " +
            "JOIN aa.account acc " +
            "JOIN AssetQuote aq ON aq.asset = a " +
            "WHERE acc.user = :user " +
            "GROUP BY a.type")
    List<CategoryTotalProjection> sumAmountByCategory(
            @Param("user") User user
    );
}
