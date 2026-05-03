package com.example.vehiclesale.service;

import com.example.vehiclesale.dto.SaleRequestDTO;
import com.example.vehiclesale.dto.SaleResponseDTO;
import com.example.vehiclesale.exception.*;
import com.example.vehiclesale.mappers.SaleMapper;
import com.example.vehiclesale.model.customers.Customer;
import com.example.vehiclesale.model.employee.Employee;
import com.example.vehiclesale.model.sale.Sale;
import com.example.vehiclesale.model.vehicle.Vehicle;
import com.example.vehiclesale.model.vehicle.VehicleStatus;
import com.example.vehiclesale.repository.CustomerRepository;
import com.example.vehiclesale.repository.EmployeeRepository;
import com.example.vehiclesale.repository.SaleRepository;
import com.example.vehiclesale.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository repository;
    private final SaleMapper mapper;
    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;
    private final VehicleRepository vehicleRepository;

    @Transactional
    public SaleResponseDTO create(SaleRequestDTO dto) {

        Employee employee = employeeRepository.findById(dto.employeeId()).orElseThrow( ()->
                new EmployeeNotFoundException("Employee not found."));

        Vehicle vehicle = vehicleRepository.findById(dto.vehicleVin()).orElseThrow( () ->
                new VehicleNotFoundException("Vehicle not found."));

        Customer customer = customerRepository.findById(dto.customerDocument()).orElseThrow( () ->
                new CustomerNotFoundException("Customer not found."));

        if(vehicle.getStatus() != VehicleStatus.AVAILABLE) {
            throw new VehicleNotAvailableException("Vehicle is not available for sale.");
        }

        vehicle.setStatus(VehicleStatus.SOLD);
        vehicleRepository.saveAndFlush(vehicle);

        Sale sale = mapper.toEntity(
                dto,
                employee,
                vehicle,
                customer);

        Sale saved = repository.save(sale);

        return mapper.toDTO(saved);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new SaleNotFoundException("Sale not found.");
        }
        repository.deleteById(id);
    }

    public List<SaleResponseDTO> listAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }
}
