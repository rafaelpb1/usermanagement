package com.example.vehiclesale.mappers;

import com.example.vehiclesale.dto.SaleRequestDTO;
import com.example.vehiclesale.dto.SaleResponseDTO;
import com.example.vehiclesale.model.customers.Customer;
import com.example.vehiclesale.model.employee.Employee;
import com.example.vehiclesale.model.sale.Sale;
import com.example.vehiclesale.model.vehicle.Vehicle;
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
