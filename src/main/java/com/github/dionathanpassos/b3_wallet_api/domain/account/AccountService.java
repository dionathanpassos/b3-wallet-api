package com.github.dionathanpassos.b3_wallet_api.domain.account;

import com.github.dionathanpassos.b3_wallet_api.config.security.AuthenticatedUser;
import com.github.dionathanpassos.b3_wallet_api.domain.account.dto.AccountRequestDTO;
import com.github.dionathanpassos.b3_wallet_api.domain.account.dto.AccountResponseDTO;
import com.github.dionathanpassos.b3_wallet_api.domain.user.User;
import com.github.dionathanpassos.b3_wallet_api.exception.BusinessException;
import com.github.dionathanpassos.b3_wallet_api.exception.ConflictException;
import com.github.dionathanpassos.b3_wallet_api.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AuthenticatedUser authenticatedUser;
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Transactional
    public AccountResponseDTO create(AccountRequestDTO requestDTO) {

        User user = getCurrentUser();
        String normalizedName = requestDTO.name().trim().toUpperCase();

        if (accountRepository.existsByUserAndNameIgnoreCase(user, normalizedName)) {
            throw new ConflictException("Já existe uma carteira com o nome informado");
        }

        Account account = Account.create(user, normalizedName, requestDTO.description());
        Account newAccount = accountRepository.save(account);

        return accountMapper.fromEntity(newAccount);
    }

    @Transactional
    public AccountResponseDTO update(Long id, AccountRequestDTO requestDTO) {

        User user = getCurrentUser();
        String normalizedName = requestDTO.name().trim().toUpperCase();

        Account account = accountRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Carteira não encontrada"));

        if (accountRepository.existsByUserAndNameIgnoreCaseAndIdNot(user, normalizedName, account.getId())) {
            throw new ConflictException("Já existe uma carteira com o nome informado");
        }

        account.update(normalizedName, requestDTO.description());

        Account accountUpdated = accountRepository.save(account);
        return accountMapper.fromEntity(accountUpdated);

    }

    @Transactional(readOnly = true)
    public Page<AccountResponseDTO> findAll(
            String name,
            Pageable pageable
    ) {
        User user = getCurrentUser();

        Page<Account> accounts = accountRepository.findAllFiltered(name, user, pageable);
        return accounts.map(accountMapper::fromEntity);
    }

    @Transactional(readOnly = true)
    public AccountResponseDTO findById(Long id) {
        User user = getCurrentUser();

        Account account = accountRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Carteira não encontrada"));

        return accountMapper.fromEntity(account);
    }

    @Transactional
    public AccountResponseDTO activate(Long id) {
        User user = getCurrentUser();

        Account account = accountRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Carteira não encontrada"));

        if (account.getStatus() == AccountStatus.ACTIVE) {
            throw new BusinessException("O registro já encontra-se ativo");
        }

        account.activate();
        return accountMapper.fromEntity(account);
    }

    @Transactional
    public AccountResponseDTO deactivate(Long id) {
        User user = getCurrentUser();

        Account account = accountRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Carteira não encontrada"));

        if (account.getStatus() == AccountStatus.INACTIVE) {
            throw new BusinessException("O registro já encontra-se inativo");
        }

        account.deactivate();
        return accountMapper.fromEntity(account);
    }

    private User getCurrentUser() {
        return authenticatedUser.getAuthenticatedUser();
    }
}
