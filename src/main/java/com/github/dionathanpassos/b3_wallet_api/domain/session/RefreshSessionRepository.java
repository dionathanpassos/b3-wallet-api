package com.github.dionathanpassos.tamplate_start_spring.domain.session;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RefreshSessionRepository extends JpaRepository<RefreshSession, Long> {
    RefreshSession findByTokenHashAndRevokedAtIsNull(String tokenHash);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM RefreshSession r WHERE r.tokenHash = :tokenHash AND r.revokedAt IS NULL")
    Optional<RefreshSession> findByTokenHashAndRevokedAtIsNullWithLock(@Param("tokenHash") String tokenHash);

    RefreshSession findByTokenHash(String refreshToken);
}
