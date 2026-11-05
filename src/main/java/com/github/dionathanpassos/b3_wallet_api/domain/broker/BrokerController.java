package com.github.dionathanpassos.b3_wallet_api.domain.broker;

import com.github.dionathanpassos.b3_wallet_api.domain.broker.dto.BrokerRequestDTO;
import com.github.dionathanpassos.b3_wallet_api.domain.broker.dto.BrokerResponseDTO;
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
@RequestMapping("/api/v1/brokers")
public class BrokerController {

    private final BrokerService brokerService;

    @PostMapping
    public ResponseEntity<BrokerResponseDTO> create(@RequestBody @Valid BrokerRequestDTO requestDTO) {
        BrokerResponseDTO created = brokerService.create(requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BrokerResponseDTO> update(@PathVariable Long id, @RequestBody @Valid BrokerRequestDTO requestDTO) {
        BrokerResponseDTO updated = brokerService.update(id, requestDTO);

        return ResponseEntity.ok(updated);
    }

    @GetMapping
    public ResponseEntity<Page<BrokerResponseDTO>> findAll(
            @RequestParam(required = false) String name,
            @PageableDefault(sort = "name", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<BrokerResponseDTO> brokers = brokerService.findAll(name, pageable);

        return ResponseEntity.ok(brokers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrokerResponseDTO> findById(@PathVariable Long id) {
        BrokerResponseDTO broker = brokerService.findById(id);

        return ResponseEntity.ok(broker);
    }
}
