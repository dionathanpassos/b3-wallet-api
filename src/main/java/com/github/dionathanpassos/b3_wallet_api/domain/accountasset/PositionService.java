package com.github.dionathanpassos.b3_wallet_api.domain.accountasset;

import com.github.dionathanpassos.b3_wallet_api.config.security.AuthenticatedUser;
import com.github.dionathanpassos.b3_wallet_api.domain.account.Account;
import com.github.dionathanpassos.b3_wallet_api.domain.asset.Asset;
import com.github.dionathanpassos.b3_wallet_api.domain.transaction.Transaction;
import com.github.dionathanpassos.b3_wallet_api.domain.transaction.TransactionType;
import com.github.dionathanpassos.b3_wallet_api.domain.user.User;
import com.github.dionathanpassos.b3_wallet_api.infra.asset.integration.AssetCatalogProvider;
import com.github.dionathanpassos.b3_wallet_api.infra.asset.integration.ExternalAssetQuoteDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionService {

    private final AccountAssetRepository accountAssetRepository;
    private final AuthenticatedUser authenticatedUser;


    @Transactional
    public void processTransaction(Transaction transaction) {

        User user = getCurrentUser();

        AccountAsset position = accountAssetRepository.findByAccountAndAssetAndAccountUserId(
                transaction.getAccount(),
                transaction.getAsset(),
                user.getId()
        ).orElseGet(
                () -> AccountAsset.create(
                        transaction.getAccount(),
                        transaction.getAsset()
                )
        );

        position.applyPosition(
                transaction.getType(),
                transaction.getQuantity(),
                transaction.getTotalAmount()
        );
        accountAssetRepository.save(position);
    }


    private User getCurrentUser() {
        return authenticatedUser.getAuthenticatedUser();
    }



}
