package com.example.usermanagement.service;

import com.example.usermanagement.dto.EmployeeRequestDTO;
import com.example.usermanagement.dto.EmployeeResponseDTO;
import com.example.usermanagement.exception.EmployeeAlreadyRegisteredException;
import com.example.usermanagement.exception.EmployeeNotFoundException;
import com.example.usermanagement.mappers.EmployeeMapper;
import com.example.usermanagement.model.employee.Employee;
import com.example.usermanagement.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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
}
