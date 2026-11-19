package com.github.dionathanpassos.b3_wallet_api.domain.dashboard.dto;

import java.math.BigDecimal;
import java.util.List;

public record DashboardAllocationDTO(
        BigDecimal totalPatrimony,
        List<AllocationByCategoryDTO> items
) {
    public record AllocationByCategoryDTO(
            String category,
            BigDecimal amount
//            BigDecimal percentage
    ) {}
}
