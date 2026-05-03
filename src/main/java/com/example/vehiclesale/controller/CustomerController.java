package com.example.vehiclesale.controller;

import com.example.vehiclesale.dto.CustomerRequestDTO;
import com.example.vehiclesale.dto.CustomerResponseDTO;
import com.example.vehiclesale.dto.CustomerUpdateDTO;
import com.example.vehiclesale.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
@Tag(name = "Customers")
public class CustomerController {

    private final CustomerService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cadastrar cliente", description = "Cadastrar novo cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos (erro de validação)"),
            @ApiResponse(responseCode = "401", description = "Não autenticado (Token ausente, inválido ou expirado)"),
            @ApiResponse(responseCode = "403", description = "Sem permissão (Requer ADMIN)"),
            @ApiResponse(responseCode = "409", description = "Conflito: Documento já cadastrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<CustomerResponseDTO> createCustomer(@RequestBody @Valid CustomerRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @DeleteMapping("/{document}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Excluir cliente", description = "Remove um cliente através do documento (Acesso: ADMIN)")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cliente excluído com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Sem permissão (Requer ADMIN)"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<CustomerResponseDTO> deleteCustomerByDocument(@PathVariable String document) {
        service.deleteByDocument(document);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Listar todos os clientes", description = "Retorna uma lista de todos os clientes cadastrados (Acesso: ADMIN, USER)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<List<CustomerResponseDTO>> listAllCustomers() {
        List<CustomerResponseDTO> result = service.listAll();

        return ResponseEntity.ok(result);
    }

    @PutMapping("/{document}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualizar cliente", description = "Atualiza os dados de um cliente existente (Acesso: ADMIN)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de atualização inválidos"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Sem permissão (Requer ADMIN)"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<CustomerResponseDTO> updateCustomer(@PathVariable String document,
                                                              @RequestBody @Valid CustomerUpdateDTO dto) {
        CustomerResponseDTO response = service.updateCustomer(document, dto);
        return ResponseEntity.ok().body(response);
    }
}
