package ru.coursework.housing.repository;

import ru.coursework.housing.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepository extends JpaRepository<Building, Long> {
}