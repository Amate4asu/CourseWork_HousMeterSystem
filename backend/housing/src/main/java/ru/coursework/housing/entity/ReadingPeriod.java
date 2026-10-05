package ru.coursework.housing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(
        name = "reading_periods",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_reading_period_year_month",
                        columnNames = {"year", "month"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
public class ReadingPeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Integer month;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
}