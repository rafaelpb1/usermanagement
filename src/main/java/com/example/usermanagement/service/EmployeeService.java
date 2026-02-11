package com.example.usermanagement.service;

import com.example.usermanagement.dto.EmployeeRequestDTO;
import com.example.usermanagement.dto.EmployeeResponseDTO;
import com.example.usermanagement.mappers.EmployeeMapper;
import com.example.usermanagement.model.employee.Employee;
import com.example.usermanagement.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository repository;
    private final EmployeeMapper mapper;

    @Transactional
    public Optional<EmployeeResponseDTO> create(EmployeeRequestDTO dto) {
        if (dto.name() != null && repository.existsByName(dto.name())) {
            return Optional.empty();
        }

        Employee employee = mapper.toEntity(dto);
        Employee saved = repository.save(employee);

        return Optional.of(mapper.toDTO(saved));
    }

    @Transactional
    public Optional<Void> deleteById(Long id) {
        Optional<Employee> employee = repository.findById(id);

        if (employee.isEmpty()) return Optional.empty();

        repository.delete(employee.get());

        return Optional.ofNullable(null);
    }

    public List<EmployeeResponseDTO> listALl() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }
}
