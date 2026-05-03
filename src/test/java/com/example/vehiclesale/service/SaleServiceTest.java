package com.example.vehiclesale.service;

import com.example.vehiclesale.dto.SaleRequestDTO;
import com.example.vehiclesale.dto.SaleResponseDTO;
import com.example.vehiclesale.exception.VehicleNotAvailableException;
import com.example.vehiclesale.mappers.SaleMapper;
import com.example.vehiclesale.model.customers.Customer;
import com.example.vehiclesale.model.employee.Employee;
import com.example.vehiclesale.model.sale.PaymentMethod;
import com.example.vehiclesale.model.sale.Sale;
import com.example.vehiclesale.model.vehicle.Vehicle;
import com.example.vehiclesale.model.vehicle.VehicleStatus;
import com.example.vehiclesale.repository.CustomerRepository;
import com.example.vehiclesale.repository.EmployeeRepository;
import com.example.vehiclesale.repository.SaleRepository;
import com.example.vehiclesale.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SaleServiceTest {

    @Mock
    private SaleRepository saleRepository;
    @Mock
    private SaleMapper saleMapper;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private SaleService saleService;

    private SaleRequestDTO request;
    private Employee employee;
    private Customer customer;
    private Vehicle vehicle;

    @BeforeEach
    void setUp() {
        request = new SaleRequestDTO(
                "1FAFP34341W207183",
                "28378947025",
                1L,
                new BigDecimal("120000.00"),
                PaymentMethod.PIX
        );

        employee = Employee.builder()
                .id(1L)
                .name("Alice")
                .position("Sales")
                .build();

        customer = Customer.builder()
                .document("28378947025")
                .name("Bob")
                .build();

        vehicle = Vehicle.builder()
                .vin("1FAFP34341W207183")
                .model("Corolla")
                .brand("Toyota")
                .licensePlate("AAA-1A11")
                .year(2025)
                .status(VehicleStatus.AVAILABLE)
                .build();
    }

    @Test
    void deveCriarVendaQuandoVeiculoEstiverDisponivel() {
        Sale sale = Sale.builder()
                .id(10L)
                .employee(employee)
                .customer(customer)
                .vehicle(vehicle)
                .saleValue(request.saleValue())
                .paymentMethod(request.paymentMethod())
                .build();

        SaleResponseDTO response = new SaleResponseDTO(
                10L,
                "Corolla",
                "Bob",
                "Alice",
                request.saleValue(),
                null
        );

        when(employeeRepository.findById(request.employeeId())).thenReturn(Optional.of(employee));
        when(vehicleRepository.findById(request.vehicleVin())).thenReturn(Optional.of(vehicle));
        when(customerRepository.findById(request.customerDocument())).thenReturn(Optional.of(customer));
        when(saleMapper.toEntity(request, employee, vehicle, customer)).thenReturn(sale);
        when(saleRepository.save(sale)).thenReturn(sale);
        when(saleMapper.toDTO(sale)).thenReturn(response);

        SaleResponseDTO result = saleService.create(request);

        assertEquals(10L, result.id());
        assertEquals(VehicleStatus.SOLD, vehicle.getStatus());
        verify(vehicleRepository).saveAndFlush(vehicle);
        verify(saleRepository).save(sale);
    }

    @Test
    void deveLancarExcecaoQuandoVeiculoNaoEstiverDisponivel() {
        vehicle.setStatus(VehicleStatus.SOLD);

        when(employeeRepository.findById(request.employeeId())).thenReturn(Optional.of(employee));
        when(vehicleRepository.findById(request.vehicleVin())).thenReturn(Optional.of(vehicle));
        when(customerRepository.findById(request.customerDocument())).thenReturn(Optional.of(customer));

        assertThrows(VehicleNotAvailableException.class, () -> saleService.create(request));

        verify(vehicleRepository, never()).saveAndFlush(any());
        verify(saleRepository, never()).save(any());
    }

    @Test
    void devePropagarConflitoDeConcorrencia() {
        when(employeeRepository.findById(request.employeeId())).thenReturn(Optional.of(employee));
        when(vehicleRepository.findById(request.vehicleVin())).thenReturn(Optional.of(vehicle));
        when(customerRepository.findById(request.customerDocument())).thenReturn(Optional.of(customer));
        when(vehicleRepository.saveAndFlush(vehicle)).thenThrow(
                new ObjectOptimisticLockingFailureException(Vehicle.class, vehicle.getVin())
        );

        assertThrows(ObjectOptimisticLockingFailureException.class, () -> saleService.create(request));

        verify(saleRepository, never()).save(any());
    }
}
