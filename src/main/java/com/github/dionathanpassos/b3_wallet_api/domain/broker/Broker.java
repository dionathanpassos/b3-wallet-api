package com.github.dionathanpassos.b3_wallet_api.domain.broker;

import com.github.dionathanpassos.b3_wallet_api.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "tb_brokers")
@Getter
public class Broker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String name;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    private Broker() {}

    public static Broker createBroker(User user, String name) {
        Broker broker = new Broker();

        broker.user = user;
        broker.name = name;

        return broker;
    }

    public void updateName(String normalizedName) {
        this.name = normalizedName;
    }
}
