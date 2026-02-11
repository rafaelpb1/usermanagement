package com.example.usermanagement.service;

import com.example.usermanagement.dto.SaleRequestDTO;
import com.example.usermanagement.dto.SaleResponseDTO;
import com.example.usermanagement.mappers.SaleMapper;
import com.example.usermanagement.model.customers.Customer;
import com.example.usermanagement.model.employee.Employee;
import com.example.usermanagement.model.sale.Sale;
import com.example.usermanagement.model.vehicle.Vehicle;
import com.example.usermanagement.repository.CustomerRepository;
import com.example.usermanagement.repository.EmployeeRepository;
import com.example.usermanagement.repository.SaleRepository;
import com.example.usermanagement.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository repository;
    private final SaleMapper mapper;
    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;
    private final VehicleRepository vehicleRepository;

    @Transactional
    public Optional<SaleResponseDTO> create(SaleRequestDTO dto) {

        Optional<Employee> employee = employeeRepository.findById(dto.employeeId());
        if (employee.isEmpty()) return Optional.empty();

        Optional<Vehicle> vehicle = vehicleRepository.findById(dto.vehicleVin());
        if (vehicle.isEmpty()) return Optional.empty();

        Optional<Customer> customer = customerRepository.findById(dto.customerDocument());
        if (customer.isEmpty()) return Optional.empty();

        Sale sale = mapper.toEntity(
                dto,
                employee.get(),
                vehicle.get(),
                customer.get() );

        Sale saved = repository.save(sale);

        return Optional.of(mapper.toDTO(saved));
    }

    @Transactional
    public boolean deleteById(Long id) {
        if (!repository.existsById(id)) return false;

        repository.deleteById(id);

        return true;
    }

    public List<SaleResponseDTO> listAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }
}
