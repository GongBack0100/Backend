package com.gongback.domain.vacancy;

import com.gongback.domain.classitem.ClassCategory;
import com.gongback.domain.classitem.ClassItem;
import com.gongback.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WaitlistRepository extends JpaRepository<Waitlist, Long> {

    List<Waitlist> findByUser(User user);

    @Query("SELECT w FROM Waitlist w WHERE (w.classItem = :classItem OR w.category = :category) AND w.isNotified = false ORDER BY w.user.trustScore DESC")
    List<Waitlist> findCandidatesForClass(ClassItem classItem, ClassCategory category);

    @Query("SELECT COUNT(w) FROM Waitlist w WHERE w.isNotified = false")
    Long countAllWaiting();
}
