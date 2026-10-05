package ru.coursework.housing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.coursework.housing.enums.ReadingSource;
import ru.coursework.housing.enums.ValidationStatus;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "meter_readings")
@Getter
@Setter
@NoArgsConstructor
public class MeterReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "meter_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_readings_meter")
    )
    private Meter meter;

    @Column(nullable = false, precision = 15, scale = 3)
    private BigDecimal value;

    @Column(name = "recorded_at", nullable = false)
    private OffsetDateTime recordedAt;

    @Column(name = "submitted_at", nullable = false)
    private OffsetDateTime submittedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "reading_source", nullable = false, length = 20)
    private ReadingSource readingSource;

    @Enumerated(EnumType.STRING)
    @Column(name = "validation_status", nullable = false, length = 20)
    private ValidationStatus validationStatus;

    @Column(name = "validation_comment", length = 500)
    private String validationComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "created_by",
            foreignKey = @ForeignKey(name = "fk_readings_user")
    )
    private User createdBy;
}