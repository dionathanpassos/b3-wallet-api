package com.github.dionathanpassos.b3_wallet_api.domain.broker;

import com.github.dionathanpassos.b3_wallet_api.domain.broker.dto.BrokerResponseDTO;
import com.github.dionathanpassos.b3_wallet_api.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BrokerRepository extends JpaRepository<Broker, Long> {
    boolean existsByUserAndNameIgnoreCase(User user, String normalizedName);

    boolean existsByUserAndNameIgnoreCaseAndIdNot(User user, String normalizedName, Long id);

    Optional<Broker> findByIdAndUser(Long id, User user);

    @Query("SELECT b FROM Broker b " +
            "WHERE b.user = :user " +
            "AND (:name IS NULL OR LOWER(b.name) LIKE LOWER(CONCAT('%', :name, '%')))")
    Page<Broker> findAllFiltered(
            @Param("name") String name,
            @Param("user") User user,
            Pageable pageable
    );
}
