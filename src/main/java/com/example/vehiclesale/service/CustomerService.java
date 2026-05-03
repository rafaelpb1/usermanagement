package com.example.vehiclesale.service;

import com.example.vehiclesale.dto.CustomerRequestDTO;
import com.example.vehiclesale.dto.CustomerResponseDTO;
import com.example.vehiclesale.dto.CustomerUpdateDTO;
import com.example.vehiclesale.exception.CustomerAlreadyRegisteredException;
import com.example.vehiclesale.exception.CustomerNotFoundException;
import com.example.vehiclesale.mappers.CustomerMapper;
import com.example.vehiclesale.model.customers.Customer;
import com.example.vehiclesale.repository.CustomerRepository;
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
