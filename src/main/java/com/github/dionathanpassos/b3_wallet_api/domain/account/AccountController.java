package com.github.dionathanpassos.b3_wallet_api.domain.account;

import com.github.dionathanpassos.b3_wallet_api.domain.account.dto.AccountRequestDTO;
import com.github.dionathanpassos.b3_wallet_api.domain.account.dto.AccountResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponseDTO> create(@RequestBody @Valid AccountRequestDTO requestDTO) {
        AccountResponseDTO created = accountService.create(requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> update(@PathVariable Long id, @RequestBody @Valid AccountRequestDTO requestDTO) {
        AccountResponseDTO updated = accountService.update(id, requestDTO);

        return ResponseEntity.ok(updated);
    }

    @GetMapping
    public ResponseEntity<Page<AccountResponseDTO>> findAll(
            @RequestParam(required = false) String name,
            @PageableDefault(sort = "name", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<AccountResponseDTO> accounts = accountService.findAll(name, pageable);

        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> findById(@PathVariable Long id) {
        AccountResponseDTO account = accountService.findById(id);

        return ResponseEntity.ok(account);
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<AccountResponseDTO> activate(@PathVariable Long id) {
        AccountResponseDTO activated = accountService.activate(id);

        return ResponseEntity.ok(activated);
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<AccountResponseDTO> deactivate(@PathVariable Long id) {
        AccountResponseDTO deactivated = accountService.deactivate(id);

        return ResponseEntity.ok(deactivated);
    }
}
