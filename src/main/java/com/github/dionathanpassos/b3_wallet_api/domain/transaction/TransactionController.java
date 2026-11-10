package com.github.dionathanpassos.b3_wallet_api.domain.transaction;

import com.github.dionathanpassos.b3_wallet_api.domain.transaction.dto.TransactionRequestDTO;
import com.github.dionathanpassos.b3_wallet_api.domain.transaction.dto.TransactionResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
