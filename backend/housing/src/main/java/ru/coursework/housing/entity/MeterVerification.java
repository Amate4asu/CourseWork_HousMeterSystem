package ru.coursework.housing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.coursework.housing.enums.VerificationResult;

import java.time.LocalDate;

@Entity
@Table(name = "meter_verifications")
@Getter
@Setter
@NoArgsConstructor
public class MeterVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "meter_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_verifications_meter")
    )
    private Meter meter;

    @Column(name = "verification_date", nullable = false)
    private LocalDate verificationDate;

    @Column(name = "document_number", nullable = false, length = 100)
    private String documentNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private VerificationResult result;

    @Column(name = "next_verification_date", nullable = false)
    private LocalDate nextVerificationDate;

    @Column(length = 1000)
    private String comment;
}