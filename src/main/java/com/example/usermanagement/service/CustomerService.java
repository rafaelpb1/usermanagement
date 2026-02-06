package com.example.usermanagement.service;

import com.example.usermanagement.dto.CustomerRequestDTO;
import com.example.usermanagement.dto.CustomerResponseDTO;
import com.example.usermanagement.mappers.CustomerMapper;
import com.example.usermanagement.model.customers.Customer;
import com.example.usermanagement.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    @Transactional
    public CustomerResponseDTO create(CustomerRequestDTO dto) {
        if (dto.document() != null && repository.existsById(dto.document())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Already exists customer registered with this document.");
        }

        Customer customer = mapper.toEntity(dto);
        Customer saved = repository.save(customer);

        return mapper.toDTO(saved);
    }

    @Transactional
    public void deleteByDocument(String document) {
        Customer customer = repository.findById(document).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Not found customer"));

        repository.delete(customer);
    }

    public List<CustomerResponseDTO> listALl() {
        List<Customer> all = repository.findAll();
        List<CustomerResponseDTO> list = all.stream().map(mapper::toDTO).toList();

        if (all.isEmpty()) throw new ResponseStatusException(
                HttpStatus.NO_CONTENT, "No customer found.");

        return list;
    }
}
