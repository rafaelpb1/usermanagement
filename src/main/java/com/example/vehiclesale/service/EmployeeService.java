package com.example.vehiclesale.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.vehiclesale.dto.EmployeeRequestDTO;
import com.example.vehiclesale.dto.EmployeeResponseDTO;
import com.example.vehiclesale.exception.EmployeeAlreadyRegisteredException;
import com.example.vehiclesale.exception.EmployeeNotFoundException;
import com.example.vehiclesale.mappers.EmployeeMapper;
import com.example.vehiclesale.model.employee.Employee;
import com.example.vehiclesale.repository.EmployeeRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository repository;
    private final EmployeeMapper mapper;

    @Transactional
    public EmployeeResponseDTO create(EmployeeRequestDTO dto) {
        if (dto.name() != null && repository.existsByName(dto.name())) {
            throw new EmployeeAlreadyRegisteredException("Employee already registered at system");
        }

        Employee employee = mapper.toEntity(dto);
        Employee saved = repository.save(employee);

        return mapper.toDTO(saved);
    }

    @Transactional
    public void deleteById(Long id) {
        Employee employee = repository.findById(id).orElseThrow(() ->
                new EmployeeNotFoundException("Employee not found."));

        repository.delete(employee);
    }

    public List<EmployeeResponseDTO> listALl() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional
    public EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO dto) {
        Employee employee = repository.findById(id).orElseThrow(() ->
                new EmployeeNotFoundException("Employee not found."));

        mapper.updateEntityFromDto(dto, employee);
        Employee saved = repository.save(employee);

        return mapper.toDTO(saved);
    }
}
