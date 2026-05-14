package com.gongback.domain.vacancy;

import com.gongback.domain.classitem.ClassSchedule;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "vacancies")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Vacancy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private ClassSchedule classSchedule;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isMatched = false;

    @CreationTimestamp
    private LocalDateTime registeredAt;

    public void markMatched() {
        this.isMatched = true;
    }
}
