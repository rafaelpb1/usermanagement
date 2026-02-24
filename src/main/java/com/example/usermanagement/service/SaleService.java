package com.example.usermanagement.service;

import com.example.usermanagement.dto.SaleRequestDTO;
import com.example.usermanagement.dto.SaleResponseDTO;
import com.example.usermanagement.exception.*;
import com.example.usermanagement.mappers.SaleMapper;
import com.example.usermanagement.model.customers.Customer;
import com.example.usermanagement.model.employee.Employee;
import com.example.usermanagement.model.sale.Sale;
import com.example.usermanagement.model.vehicle.Vehicle;
import com.example.usermanagement.model.vehicle.VehicleStatus;
import com.example.usermanagement.repository.CustomerRepository;
import com.example.usermanagement.repository.EmployeeRepository;
import com.example.usermanagement.repository.SaleRepository;
import com.example.usermanagement.repository.VehicleRepository;
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
    VehicleStatus vehicleStatus;

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
