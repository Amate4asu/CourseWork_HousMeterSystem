package ru.coursework.housing.repository;

import ru.coursework.housing.entity.MeterReading;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface MeterReadingRepository extends JpaRepository<MeterReading, Long> {

    Optional<MeterReading> findTopByMeterIdOrderByRecordedAtDescIdDesc(
            Long meterId
    );

    List<MeterReading> findAllByMeterIdOrderByRecordedAtDescIdDesc(
            Long meterId
    );

    List<MeterReading> findAllByMeterIdAndRecordedAtBetweenOrderByRecordedAtAsc(
            Long meterId,
            OffsetDateTime from,
            OffsetDateTime to
    );
}