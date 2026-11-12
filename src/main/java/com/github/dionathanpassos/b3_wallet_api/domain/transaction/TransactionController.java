package com.github.dionathanpassos.b3_wallet_api.domain.transaction;

import com.github.dionathanpassos.b3_wallet_api.domain.transaction.dto.TransactionRequestDTO;
import com.github.dionathanpassos.b3_wallet_api.domain.transaction.dto.TransactionResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> create(@RequestBody @Valid TransactionRequestDTO requestDTO) {
        TransactionResponseDTO created = transactionService.create(requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<Page<TransactionResponseDTO>> findAll(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long accountId,
            @RequestParam(required = false) Long assetId,
            @RequestParam(required = false) TransactionType type,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @PageableDefault(sort = "transactionDate", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<TransactionResponseDTO> transactions = transactionService.findAll(search, accountId, assetId, type, startDate, endDate, pageable);

        return ResponseEntity.ok(transactions);
    }
}
