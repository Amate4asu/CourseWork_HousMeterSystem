package ru.coursework.housing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "apartments",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_apartment_building_number",
                        columnNames = {"building_id", "apartment_number"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_apartments_user")
    )
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "building_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_apartments_building")
    )
    private Building building;

    @Column(name = "apartment_number", nullable = false, length = 20)
    private String apartmentNumber;

    @OneToMany(
            mappedBy = "apartment",
            fetch = FetchType.LAZY
    )
    private List<Meter> meters = new ArrayList<>();
}