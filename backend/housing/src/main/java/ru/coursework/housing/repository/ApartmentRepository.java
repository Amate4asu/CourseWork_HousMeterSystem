package ru.coursework.housing.repository;

import ru.coursework.housing.entity.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApartmentRepository extends JpaRepository<Apartment, Long> {

    List<Apartment> findAllByOwnerId(Long ownerId);

    List<Apartment> findAllByBuildingId(Long buildingId);

    Optional<Apartment> findByIdAndOwnerId(Long apartmentId, Long ownerId);

    boolean existsByBuildingIdAndApartmentNumber(
            Long buildingId,
            String apartmentNumber
    );
}