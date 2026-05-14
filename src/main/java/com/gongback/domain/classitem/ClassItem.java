package com.gongback.domain.classitem;

import com.gongback.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "class_items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class ClassItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String studio;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClassCategory category;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer capacity;

    private Integer durationMinutes;

    @Builder.Default
    private Double rating = 4.5;

    @Builder.Default
    private Integer reviewCount = 0;

    private String locationAddress;

    private Double lat;
    private Double lng;

    private String imageUrl;
    private String emoji;
    private String tags;
    private String recurringSchedule;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id", nullable = false)
    private User host;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
