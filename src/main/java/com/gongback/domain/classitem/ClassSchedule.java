package com.gongback.domain.classitem;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "class_schedules")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class ClassSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false)
    private ClassItem classItem;

    @Column(nullable = false)
    private LocalDateTime scheduledAt;

    @Column(nullable = false)
    private Integer maxCapacity;

    @Column(nullable = false)
    @Builder.Default
    private Integer bookedCount = 0;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    public int remainingSeats() {
        return maxCapacity - bookedCount;
    }

    public boolean isFull() {
        return bookedCount >= maxCapacity;
    }

    public void incrementBooked() {
        this.bookedCount++;
    }

    public void decrementBooked() {
        if (this.bookedCount > 0) this.bookedCount--;
    }
}
