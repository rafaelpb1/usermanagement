package com.example.usermanagement.repository;

import com.example.usermanagement.model.sale.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findByEmployeeId(String document);

    boolean existsByVehicleVin(String s);
}
