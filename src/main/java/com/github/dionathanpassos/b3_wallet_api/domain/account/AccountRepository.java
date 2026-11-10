package com.github.dionathanpassos.b3_wallet_api.domain.account;

import com.github.dionathanpassos.b3_wallet_api.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByUserAndNameIgnoreCase(User user, String normalizedName);

    Optional<Account> findByIdAndUser(Long id, User user);

    boolean existsByUserAndNameIgnoreCaseAndIdNot(User user, String normalizedName, Long id);

    @Query("SELECT a FROM Account a " +
            "WHERE a.user = :user " +
            "AND (:name IS NULL OR LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%')))")
    Page<Account> findAllFiltered(
            @Param("name") String name,
            @Param("user") User user,
            Pageable pageable
    );
}
