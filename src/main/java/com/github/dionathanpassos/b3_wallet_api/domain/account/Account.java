package com.github.dionathanpassos.b3_wallet_api.domain.account;

import com.github.dionathanpassos.b3_wallet_api.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "tb_accounts")
@Getter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    protected Account() {}

    public static Account create(User user, String normalizedName, String description) {
        Account account = new Account();

        account.user = user;
        account.name = normalizedName;
        account.description = (description != null) ? description : "";
        account.status = AccountStatus.ACTIVE;

        return account;

    }

    public void update(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void activate() {
        this.status = AccountStatus.ACTIVE;
    }
    public void deactivate() {
        this.status = AccountStatus.INACTIVE;
    }
}
