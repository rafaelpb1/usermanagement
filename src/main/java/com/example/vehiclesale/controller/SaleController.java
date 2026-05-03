package com.example.usermanagement.controller;

import com.example.usermanagement.dto.SaleRequestDTO;
import com.example.usermanagement.dto.SaleResponseDTO;
import com.example.usermanagement.service.SaleService;
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
@RequestMapping("/sales")
@RequiredArgsConstructor
@Tag(name = "Sales")
public class SaleController {

    private final SaleService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Registrar venda", description = "Cria um novo registro de venda no sistema (Acesso: ADMIN)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Venda registrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da venda inválidos (ex: IDs de cliente ou veículo inexistentes)"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Sem permissão (Requer ADMIN)"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<?> createSale(@RequestBody @Valid SaleRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Excluir venda", description = "Remove o registro de uma venda via ID (Acesso: ADMIN)")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Registro de venda excluído com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Sem permissão (Requer ADMIN)"),
            @ApiResponse(responseCode = "404", description = "Venda não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<Void> deleteSale(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Listar todas as vendas", description = "Retorna o histórico completo de vendas (Acesso: ADMIN, USER)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Histórico de vendas retornado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<List<SaleResponseDTO>> listAllSales() {
        List<SaleResponseDTO> result = service.listAll();

        return ResponseEntity.ok(result);
    }
}
