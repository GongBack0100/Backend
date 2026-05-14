package com.gongback.domain.payment;

import com.gongback.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "saved_payment_methods")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class SavedPaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod type;

    private String cardLast4;
    private String cardBrand;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isDefault = false;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
