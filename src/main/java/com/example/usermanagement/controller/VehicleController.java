package com.example.usermanagement.controller;

import com.example.usermanagement.dto.EmployeeResponseDTO;
import com.example.usermanagement.dto.VehicleRequestDTO;
import com.example.usermanagement.dto.VehicleResponseDTO;
import com.example.usermanagement.dto.VehicleUpdateDTO;
import com.example.usermanagement.exception.ErrorResponse;
import com.example.usermanagement.service.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
@Tag(name = "Vehicles")
@SecurityRequirement(name = "bearerAuth")
public class VehicleController {

    private final VehicleService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cadastrar veículo", description = "Cadastra um novo veículo no sistema (Acesso: ADMIN)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Veículo cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos (erro de validação)"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Sem permissão (Requer ADMIN)"),
            @ApiResponse(responseCode = "409", description = "Conflito: Veículo com este VIN já cadastrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<VehicleResponseDTO> createVehicle(@RequestBody @Valid VehicleRequestDTO dto) {
        VehicleResponseDTO response = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{vin}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Excluir veículo", description = "Remove um veículo através do VIN (Acesso: ADMIN)")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Veículo excluído com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Sem permissão (Requer ADMIN)"),
            @ApiResponse(responseCode = "404", description = "Veículo não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<Void> deleteVehicleByVin(@PathVariable String vin) {
        service.deleteByVIN(vin);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar veículos", description = "Retorna todos os veículos da frota (Acesso: ADMIN, USER)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<List<VehicleResponseDTO>> listAllVehicles() {
        List<VehicleResponseDTO> response = service.listAllVehicles();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{vin}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualizar veículo", description = "Atualiza os dados de um veículo existente via VIN (Acesso: ADMIN)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Veículo atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de atualização inválidos"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Sem permissão (Requer ADMIN)"),
            @ApiResponse(responseCode = "404", description = "Veículo não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<VehicleResponseDTO> updateVehicle(@PathVariable String vin,
                                                            @RequestBody @Valid VehicleUpdateDTO dto ) {
        VehicleResponseDTO response = service.updateVehicle(vin, dto);
        return ResponseEntity.ok(response);
    }
}
