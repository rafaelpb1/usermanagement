package com.example.usermanagement.service;

import com.example.usermanagement.dto.CustomerRequestDTO;
import com.example.usermanagement.dto.CustomerResponseDTO;
import com.example.usermanagement.dto.CustomerUpdateDTO;
import com.example.usermanagement.exception.CustomerAlreadyRegisteredException;
import com.example.usermanagement.exception.CustomerNotFoundException;
import com.example.usermanagement.mappers.CustomerMapper;
import com.example.usermanagement.model.customers.Customer;
import com.example.usermanagement.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    @Transactional
    public CustomerResponseDTO create(CustomerRequestDTO dto) {
        if (dto.document() != null && repository.existsById(dto.document())) {
            throw new CustomerAlreadyRegisteredException("Customer already registered at system");
        }

        Customer customer = mapper.toEntity(dto);
        Customer saved = repository.save(customer);

        return mapper.toDTO(saved);
    }

    @Transactional
    public void deleteByDocument(String document) {
        Customer customer = repository.findById(document).orElseThrow(() ->
                new CustomerNotFoundException("Customer not found."));

        repository.delete(customer);
    }

    public List<CustomerResponseDTO> listAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional
    public CustomerResponseDTO updateCustomer(String document, CustomerUpdateDTO dto) {
        Customer customer = repository.findById(document).orElseThrow(() ->
                new CustomerNotFoundException("Customer not found."));

        mapper.updateEntityFromDto(dto, customer);
        Customer saved = repository.save(customer);

        return mapper.toDTO(saved);
    }

}
