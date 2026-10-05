package ru.coursework.housing.repository;

import ru.coursework.housing.entity.ReadingPeriod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReadingPeriodRepository
        extends JpaRepository<ReadingPeriod, Long> {

    Optional<ReadingPeriod> findByYearAndMonth(
            Integer year,
            Integer month
    );
}