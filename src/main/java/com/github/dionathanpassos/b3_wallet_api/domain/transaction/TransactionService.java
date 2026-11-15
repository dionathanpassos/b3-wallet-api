package com.github.dionathanpassos.b3_wallet_api.domain.transaction;

import com.github.dionathanpassos.b3_wallet_api.config.security.AuthenticatedUser;
import com.github.dionathanpassos.b3_wallet_api.domain.account.Account;
import com.github.dionathanpassos.b3_wallet_api.domain.account.AccountRepository;
import com.github.dionathanpassos.b3_wallet_api.domain.account.AccountStatus;
import com.github.dionathanpassos.b3_wallet_api.domain.accountasset.AccountAsset;
import com.github.dionathanpassos.b3_wallet_api.domain.accountasset.AccountAssetRepository;
import com.github.dionathanpassos.b3_wallet_api.domain.accountasset.PositionService;
import com.github.dionathanpassos.b3_wallet_api.domain.asset.Asset;
import com.github.dionathanpassos.b3_wallet_api.domain.asset.AssetRepository;
import com.github.dionathanpassos.b3_wallet_api.domain.assetquote.AssetQuoteService;
import com.github.dionathanpassos.b3_wallet_api.domain.transaction.dto.TransactionRequestDTO;
import com.github.dionathanpassos.b3_wallet_api.domain.transaction.dto.TransactionResponseDTO;
import com.github.dionathanpassos.b3_wallet_api.domain.user.User;
import com.github.dionathanpassos.b3_wallet_api.exception.BusinessException;
import com.github.dionathanpassos.b3_wallet_api.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final AssetRepository assetRepository;
    private final AuthenticatedUser authenticatedUser;
    private final TransactionMapper transactionMapper;
    private final PositionService positionService;
    private final AccountAssetRepository accountAssetRepository;

    private final AssetQuoteService assetQuoteService;

    @Transactional
    public TransactionResponseDTO create(TransactionRequestDTO requestDTO) {

        User user = getCurrentUser();

        Account account = accountRepository.findByIdAndUser(requestDTO.accountId(),user)
                .orElseThrow(() -> new ResourceNotFoundException("Carteira não encontrada"));

        if (account.getStatus() == AccountStatus.INACTIVE) {
            throw new BusinessException("Carteira inativa, não é possível realizar movimentações");
        }

        Asset asset = assetRepository.findById(requestDTO.assetId())
                .orElseThrow(() -> new ResourceNotFoundException("Ativo não encontrado"));


        BigDecimal totalAmount = requestDTO.unitPrice().multiply(BigDecimal.valueOf(requestDTO.quantity()));

        Transaction transaction = Transaction.create(
                account,
                asset,
                requestDTO.type(),
                requestDTO.quantity(),
                requestDTO.unitPrice(),
                totalAmount,
                requestDTO.transactionDate()
        );

        Transaction saved = transactionRepository.save(transaction);
        positionService.processTransaction(saved);
        return transactionMapper.fromEntity(saved);
    }

    @Transactional(readOnly = true)
    public Page<TransactionResponseDTO> findAll(
            String search,
            Long accountId,
            Long assetId,
            TransactionType type,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable
    ) {
        User user = getCurrentUser();

        Page<Transaction> transactions = transactionRepository.findAllFiltered(search,accountId,assetId, type, startDate, endDate, user, pageable);

        return transactions.map(transactionMapper::fromEntity);
    }

    @Transactional(readOnly = true)
    public TransactionResponseDTO findById(Long id) {
        User user = getCurrentUser();

        Transaction transaction = transactionRepository.findByIdAndAccountUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Transação não encontrada"));

        return transactionMapper.fromEntity(transaction);
    }

    @Transactional
    public void delete(Long id) {
        User user = getCurrentUser();

        Transaction transaction = transactionRepository.findByIdAndAccountUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Transação não encontrada"));

        List<Transaction> transactions = transactionRepository.findAllByAccountUserAndAssetAndIdNotOrderByTransactionDateAsc(
                user,
                transaction.getAsset(),
                transaction.getId()
        );

        try {
            recalculate(transactions);
        } catch (BusinessException ex) {
            throw new BusinessException("Esta transação não pode ser removida porque existem operações mais recentes vinculadas a este ativo. Exclua as transações mais novas primeiro.");
        }

        transactionRepository.delete(transaction);
    }

    @Transactional
    private void recalculate(List<Transaction> transactions) {
        User user = getCurrentUser();

        AccountAsset position = accountAssetRepository.findByAccountAndAssetAndAccountUserId(
                transactions.getFirst().getAccount(),
                transactions.getFirst().getAsset(),
                user.getId()
        ).orElseThrow(() ->
                new BusinessException("Não existe posição deste ativo nesta carteira")
        );

        position.resetPosition();

        for (Transaction transaction : transactions) {
            positionService.processTransaction(transaction);
        }
    }

    private User getCurrentUser() {
        return authenticatedUser.getAuthenticatedUser();
    }
}
