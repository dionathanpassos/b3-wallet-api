package com.github.dionathanpassos.b3_wallet_api.domain.dashboard;


import com.github.dionathanpassos.b3_wallet_api.domain.dashboard.dto.DashboardAllocationDTO;
import com.github.dionathanpassos.b3_wallet_api.domain.dashboard.dto.SummaryDashboardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/summary")
    public ResponseEntity<SummaryDashboardDTO> getSummary() {
        SummaryDashboardDTO summary = dashboardService.getSummary();

        return ResponseEntity.ok(summary);

    }

    @GetMapping("/allocation")
    public ResponseEntity<DashboardAllocationDTO> getAllocation() {
        DashboardAllocationDTO allocation = dashboardService.getAllocation();

        return ResponseEntity.ok(allocation);
    }
}
