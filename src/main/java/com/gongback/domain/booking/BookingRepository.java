package com.gongback.domain.booking;

import com.gongback.domain.classitem.ClassSchedule;
import com.gongback.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserOrderByCreatedAtDesc(User user);

    boolean existsByUserAndClassScheduleAndStatusNot(User user, ClassSchedule schedule, BookingStatus status);

    @Query("SELECT b FROM Booking b WHERE b.classSchedule.classItem.host.id = :hostId ORDER BY b.classSchedule.scheduledAt")
    List<Booking> findByHostId(Long hostId);

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.classSchedule.classItem.host.id = :hostId AND b.status = :status")
    Long countByHostIdAndStatus(Long hostId, BookingStatus status);

    Optional<Booking> findByIdAndUser(Long id, User user);

    long countByUser(User user);
    long countByUserAndStatus(User user, BookingStatus status);
    List<Booking> findByClassSchedule(ClassSchedule schedule);
}
