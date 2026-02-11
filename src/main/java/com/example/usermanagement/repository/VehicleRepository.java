package com.example.usermanagement.repository;

import com.example.usermanagement.model.vehicle.Vehicle;
import com.example.usermanagement.model.vehicle.VehicleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> {

    List<Vehicle> findByStatus(VehicleStatus status);

    Optional<Vehicle> findByVin(String vin);

    boolean existsByVin(String vin);
}
