package com.gongback.domain.classitem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ClassScheduleRepository extends JpaRepository<ClassSchedule, Long> {
    List<ClassSchedule> findByClassItem(ClassItem classItem);

    @Query("SELECT s FROM ClassSchedule s WHERE s.classItem.host.id = :hostId AND s.scheduledAt >= :from AND s.scheduledAt < :to AND s.isActive = true ORDER BY s.scheduledAt")
    List<ClassSchedule> findByHostAndDate(Long hostId, LocalDateTime from, LocalDateTime to);

    @Query("SELECT s FROM ClassSchedule s WHERE s.bookedCount < s.maxCapacity AND s.scheduledAt > :now AND s.isActive = true ORDER BY s.scheduledAt")
    List<ClassSchedule> findAvailable(LocalDateTime now);
}
