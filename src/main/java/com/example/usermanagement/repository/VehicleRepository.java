package com.example.usermanagement.repository;

import com.example.usermanagement.model.vehicle.Vehicle;
import com.example.usermanagement.model.vehicle.VehicleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> {

    List<Vehicle> findByStatus(VehicleStatus status);

    boolean existsByVin(String vin);
}
