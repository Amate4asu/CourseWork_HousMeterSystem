package ru.coursework.housing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.coursework.housing.enums.MeterType;
import ru.coursework.housing.enums.PlacementType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "meters")
@Getter
@Setter
@NoArgsConstructor
public class Meter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serial_number", nullable = false, unique = true, length = 100)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "meter_type", nullable = false, length = 30)
    private MeterType meterType;

    @Enumerated(EnumType.STRING)
    @Column(name = "placement_type", nullable = false, length = 20)
    private PlacementType placementType;

    @Column(name = "installation_date", nullable = false)
    private LocalDate installationDate;

    @Column(name = "next_verification_date", nullable = false)
    private LocalDate nextVerificationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "apartment_id",
            foreignKey = @ForeignKey(name = "fk_meters_apartment")
    )
    private Apartment apartment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "building_id",
            foreignKey = @ForeignKey(name = "fk_meters_building")
    )
    private Building building;

    @OneToMany(
            mappedBy = "meter",
            fetch = FetchType.LAZY
    )
    private List<MeterReading> readings = new ArrayList<>();

    @OneToMany(
            mappedBy = "meter",
            fetch = FetchType.LAZY
    )
    private List<MeterVerification> verifications = new ArrayList<>();
}