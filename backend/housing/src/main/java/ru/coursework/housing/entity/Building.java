package ru.coursework.housing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "buildings")
@Getter
@Setter
@NoArgsConstructor
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String address;

    @OneToMany(
            mappedBy = "building",
            fetch = FetchType.LAZY
    )
    private List<Apartment> apartments = new ArrayList<>();

    @OneToMany(
            mappedBy = "building",
            fetch = FetchType.LAZY
    )
    private List<Meter> meters = new ArrayList<>();
}