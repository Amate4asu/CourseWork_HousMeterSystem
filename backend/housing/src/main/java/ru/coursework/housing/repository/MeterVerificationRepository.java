package ru.coursework.housing.repository;

import ru.coursework.housing.entity.MeterVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MeterVerificationRepository
        extends JpaRepository<MeterVerification, Long> {

    List<MeterVerification> findAllByMeterIdOrderByVerificationDateDesc(
            Long meterId
    );

    Optional<MeterVerification> findTopByMeterIdOrderByVerificationDateDescIdDesc(
            Long meterId
    );
}