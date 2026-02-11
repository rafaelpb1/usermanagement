package com.example.usermanagement.repository;

import com.example.usermanagement.model.sale.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    Optional<Sale> findByEmployeeId(String id);

    Optional<Sale> findByVehicleVin(String vin);

    Optional<Sale> findByCustomerDocument(String document);
}
