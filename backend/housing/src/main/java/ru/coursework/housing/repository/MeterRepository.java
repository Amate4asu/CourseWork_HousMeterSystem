package ru.coursework.housing.repository;

import ru.coursework.housing.entity.Meter;
import ru.coursework.housing.enums.PlacementType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MeterRepository extends JpaRepository<Meter, Long> {

    Optional<Meter> findBySerialNumber(String serialNumber);

    boolean existsBySerialNumber(String serialNumber);

    List<Meter> findAllByApartmentId(Long apartmentId);

    List<Meter> findAllByBuildingId(Long buildingId);

    List<Meter> findAllByApartmentOwnerId(Long ownerId);

    List<Meter> findAllByPlacementType(PlacementType placementType);

    Optional<Meter> findByIdAndApartmentOwnerId(Long meterId, Long ownerId);
}