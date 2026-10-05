package ru.coursework.housing.repository;

import ru.coursework.housing.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository
        extends JpaRepository<Notification, Long> {

    List<Notification> findAllByUserIdOrderByCreatedAtDesc(
            Long userId
    );

    long countByUserIdAndReadAtIsNull(Long userId);
}