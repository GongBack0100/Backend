package com.gongback.domain.favorite;

import com.gongback.domain.classitem.ClassItem;
import com.gongback.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUser(User user);
    Optional<Favorite> findByUserAndClassItem(User user, ClassItem classItem);
    boolean existsByUserAndClassItem(User user, ClassItem classItem);
}
