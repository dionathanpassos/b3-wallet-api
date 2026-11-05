package com.github.dionathanpassos.b3_wallet_api.domain.broker;

import com.github.dionathanpassos.b3_wallet_api.config.security.AuthenticatedUser;
import com.github.dionathanpassos.b3_wallet_api.domain.broker.dto.BrokerRequestDTO;
import com.github.dionathanpassos.b3_wallet_api.domain.broker.dto.BrokerResponseDTO;
import com.github.dionathanpassos.b3_wallet_api.domain.user.User;
import com.github.dionathanpassos.b3_wallet_api.exception.ConflictException;
import com.github.dionathanpassos.b3_wallet_api.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BrokerService {

    private final AuthenticatedUser authenticatedUser;
    private final BrokerRepository brokerRepository;
    private final BrokerMapper brokerMapper;

    @Transactional
    public BrokerResponseDTO create(BrokerRequestDTO requestDTO) {

        User user = getCurrentUser();
        String normalizedName = requestDTO.name().trim().toUpperCase();

        if (brokerRepository.existsByUserAndNameIgnoreCase(user, normalizedName)) {
            throw new ConflictException("Já existe uma corretora com o nome informado");
        }

        Broker broker = Broker.createBroker(user, normalizedName);
        Broker newBroker = brokerRepository.save(broker);
        return brokerMapper.fromEntity(newBroker);
    }

    @Transactional
    public BrokerResponseDTO update(Long id, @Valid BrokerRequestDTO requestDTO) {

        User user = getCurrentUser();
        String normalizedName = requestDTO.name().trim().toUpperCase();

        Broker broker = brokerRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Corretora não encontrada"));

        if (brokerRepository.existsByUserAndNameIgnoreCaseAndIdNot(user, normalizedName, broker.getId())) {
            throw new ConflictException("Já existe uma corretora com o nome informado");
        }

        broker.updateName(normalizedName);

        Broker updatedBroker = brokerRepository.save(broker);
        return brokerMapper.fromEntity(updatedBroker);

    }

    @Transactional(readOnly = true)
    public Page<BrokerResponseDTO> findAll(String name, Pageable pageable) {
        User user = getCurrentUser();

        Page<Broker> brokers = brokerRepository.findAllFiltered(name, user, pageable);

        return brokers.map(brokerMapper::fromEntity);
    }

    @Transactional(readOnly = true)
    public BrokerResponseDTO findById(Long id) {
        User user = getCurrentUser();

        Broker broker = brokerRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Corretora nao encontrada"));

        return brokerMapper.fromEntity(broker);
    }

    private User getCurrentUser() {
        return authenticatedUser.getAuthenticatedUser();
    }
}
