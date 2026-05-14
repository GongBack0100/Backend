package com.gongback.domain.vacancy;

import com.gongback.domain.classitem.ClassCategory;
import com.gongback.domain.classitem.ClassItem;
import com.gongback.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "waitlists")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Waitlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    private ClassItem classItem;

    @Enumerated(EnumType.STRING)
    private ClassCategory category;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isNotified = false;

    @CreationTimestamp
    private LocalDateTime joinedAt;

    public void markNotified() {
        this.isNotified = true;
    }
}
