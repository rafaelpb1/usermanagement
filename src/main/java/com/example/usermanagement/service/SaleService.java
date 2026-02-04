package com.example.usermanagement.service;

import com.example.usermanagement.mappers.SaleMapper;
import com.example.usermanagement.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository repository;
    private final SaleMapper mapper;

}
