package com.gongback.domain.vacancy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VacancyRepository extends JpaRepository<Vacancy, Long> {

    @Query("SELECT v FROM Vacancy v WHERE v.classSchedule.classItem.host.id = :hostId AND v.isMatched = false ORDER BY v.registeredAt DESC")
    List<Vacancy> findUnmatchedByHostId(Long hostId);

    @Query("SELECT COUNT(v) FROM Vacancy v WHERE v.classSchedule.classItem.host.id = :hostId AND v.isMatched = false")
    Long countUnmatchedByHostId(Long hostId);
}
