package com.example.vehiclesale.repository;

import com.example.vehiclesale.model.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByName(String name);

}
