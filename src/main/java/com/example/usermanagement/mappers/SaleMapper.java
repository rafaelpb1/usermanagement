package com.example.usermanagement.mappers;

import com.example.usermanagement.dto.SaleRequestDTO;
import com.example.usermanagement.dto.SaleResponseDTO;
import com.example.usermanagement.model.customers.Customer;
import com.example.usermanagement.model.employee.Employee;
import com.example.usermanagement.model.sale.Sale;
import com.example.usermanagement.model.vehicle.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SaleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "saleDate", ignore = true)
    Sale toEntity(SaleRequestDTO dto,
                  Employee employee,
                  Vehicle vehicle,
                  Customer customer);

    @Mapping(target = "vehicleModel", source = "vehicle.model")
    @Mapping(target = "customerName", source = "customer.name")
    @Mapping(target = "employeeName", source = "employee.name")
    SaleResponseDTO toDTO(Sale entity);
}
