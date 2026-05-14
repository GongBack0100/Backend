package com.gongback.domain.payment;

import com.gongback.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SavedPaymentMethodRepository extends JpaRepository<SavedPaymentMethod, Long> {
    List<SavedPaymentMethod> findByUser(User user);
}
