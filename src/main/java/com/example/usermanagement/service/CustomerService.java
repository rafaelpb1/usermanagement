package com.example.usermanagement.service;

import com.example.usermanagement.dto.CustomerRequestDTO;
import com.example.usermanagement.dto.CustomerResponseDTO;
import com.example.usermanagement.mappers.CustomerMapper;
import com.example.usermanagement.model.customers.Customer;
import com.example.usermanagement.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    @Transactional
    public Optional<CustomerResponseDTO> create(CustomerRequestDTO dto) {
        if (dto.document() != null && repository.existsById(dto.document())) {
            return Optional.empty();
        }

        Customer customer = mapper.toEntity(dto);
        Customer saved = repository.save(customer);

        return Optional.of(mapper.toDTO(saved));
    }

    @Transactional
    public Optional<Void> deleteByDocument(String document) {
        Optional<Customer> customer = repository.findById(document);

        if (customer.isEmpty()) return Optional.empty();

        repository.delete(customer.get());

        return Optional.ofNullable(null);
    }

    public List<CustomerResponseDTO> listAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }
}
