package com.github.dionathanpassos.b3_wallet_api.domain.dashboard.dto;

import java.math.BigDecimal;

public record SummaryDashboardDTO(
        BigDecimal totalPatrimony,
        BigDecimal totalInvested,
        BigDecimal totalProfit,
        BigDecimal profitability
) {
}
