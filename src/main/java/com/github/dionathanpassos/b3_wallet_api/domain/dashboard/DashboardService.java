package com.github.dionathanpassos.b3_wallet_api.domain.dashboard;

import com.github.dionathanpassos.b3_wallet_api.config.security.AuthenticatedUser;
import com.github.dionathanpassos.b3_wallet_api.domain.accountasset.AccountAsset;
import com.github.dionathanpassos.b3_wallet_api.domain.accountasset.AccountAssetRepository;
import com.github.dionathanpassos.b3_wallet_api.domain.asset.Asset;
import com.github.dionathanpassos.b3_wallet_api.domain.assetquote.AssetQuote;
import com.github.dionathanpassos.b3_wallet_api.domain.assetquote.AssetQuoteRepository;
import com.github.dionathanpassos.b3_wallet_api.domain.dashboard.dto.DashboardAllocationDTO;
import com.github.dionathanpassos.b3_wallet_api.domain.dashboard.dto.SummaryDashboardDTO;
import com.github.dionathanpassos.b3_wallet_api.domain.dashboard.projection.CategoryTotalProjection;
import com.github.dionathanpassos.b3_wallet_api.domain.user.User;
import com.github.dionathanpassos.b3_wallet_api.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final AuthenticatedUser authenticatedUser;
    private final AccountAssetRepository accountAssetRepository;
    private final AssetQuoteRepository assetQuoteRepository;

    @Transactional(readOnly = true)
    public SummaryDashboardDTO getSummary() {
        User user = getCurrentUser();

        List<AccountAsset> positions = accountAssetRepository.findAllByAccountUser(user);

        BigDecimal totalPatrimony = getPatrimony(positions);
        BigDecimal totalInvested = getTotalInvested(positions);
        BigDecimal totalProfit = totalPatrimony.subtract(totalInvested);
        BigDecimal profitability = totalProfit.divide(totalPatrimony, 4, RoundingMode.HALF_EVEN)
                .multiply(BigDecimal.valueOf(100))
                .setScale(2, RoundingMode.HALF_EVEN);

        return new SummaryDashboardDTO(
                totalPatrimony,
                totalInvested,
                totalProfit,
                profitability
        );

    }

    @Transactional(readOnly = true)
    public DashboardAllocationDTO getAllocation() {
        User user = getCurrentUser();

        List<AccountAsset> positions = accountAssetRepository.findAllByAccountUser(user);

        BigDecimal totalPatrimony = getPatrimony(positions);

        List<CategoryTotalProjection> projections = accountAssetRepository.sumAmountByCategory(user);

        List<DashboardAllocationDTO.AllocationByCategoryDTO> items = projections.stream()
                .map(proj -> {
                    return new DashboardAllocationDTO.AllocationByCategoryDTO(
                            proj.getCategory().name(),
                            proj.getAmount()
                    );
                }).toList();

        return new DashboardAllocationDTO(
                totalPatrimony,
                items
        );
    }

    private BigDecimal getPatrimony(List<AccountAsset> positions) {

        List<Asset> assets = positions.stream()
                .map(AccountAsset::getAsset)
                .toList();

        List<AssetQuote> assetQuotes = assetQuoteRepository.findAllByAssetIn(assets);

        Map<Long, BigDecimal> priceByAssetId = assetQuotes.stream()
                .collect(Collectors.toMap(
                        quote -> quote.getAsset().getId(),
                        AssetQuote::getPrice,
                        (existing, replacement) -> replacement
                ));

        return positions.stream()
                .map(position -> {
                    BigDecimal price = priceByAssetId.getOrDefault(position.getAsset().getId(), BigDecimal.ZERO);
                    return BigDecimal.valueOf(position.getQuantity()).multiply(price);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);


    }

    private BigDecimal getTotalInvested(List<AccountAsset> positions) {
        return positions.stream()
                .map(AccountAsset::getTotalInvested)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private User getCurrentUser() {
        return authenticatedUser.getAuthenticatedUser();
    }
}
