package com.gongback.domain.classitem;

import com.gongback.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassRepository extends JpaRepository<ClassItem, Long> {
    List<ClassItem> findByCategory(ClassCategory category);
    List<ClassItem> findByHost(User host);
    List<ClassItem> findByIsActiveTrue();
    List<ClassItem> findByCategoryAndIsActiveTrue(ClassCategory category);
}
