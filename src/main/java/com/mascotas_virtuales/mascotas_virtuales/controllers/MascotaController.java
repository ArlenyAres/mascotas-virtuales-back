package com.mascotas_virtuales.mascotas_virtuales.controllers;

import com.mascotas_virtuales.mascotas_virtuales.dto.MascotaPersonalizadaRequest;
import com.mascotas_virtuales.mascotas_virtuales.models.MascotaVirtual;
import com.mascotas_virtuales.mascotas_virtuales.services.MascotaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mascotas")
public class MascotaController {

    private final MascotaService mascotaService;

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    @Operation(summary = "Obtener mascotas predefinidas", description = "Devuelve una lista de mascotas predefinidas disponibles.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de mascotas predefinidas devuelta correctamente",
                    content = @Content(schema = @Schema(implementation = MascotaVirtual.class)))
    })
    @GetMapping("/predefinidas")
    public ResponseEntity<List<MascotaVirtual>> getMascotasPredefinidas() {
        return ResponseEntity.ok(mascotaService.getMascotasPredefinidas());
    }

    @Operation(summary = "Crear una mascota personalizada", description = "Crea una nueva mascota personalizada para el usuario autenticado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mascota personalizada creada correctamente",
                    content = @Content(schema = @Schema(implementation = MascotaVirtual.class))),
            @ApiResponse(responseCode = "400", description = "Datos de personalización inválidos")
    })
    @PostMapping("/personalizar")
    public ResponseEntity<MascotaVirtual> crearMascotaPersonalizada(@RequestBody MascotaPersonalizadaRequest request) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MascotaVirtual nuevaMascota = mascotaService.crearMascotaPersonalizada(request.getMascotaId(), request.getNombre(), userDetails);
        return ResponseEntity.ok(nuevaMascota);
    }

    @Operation(summary = "Obtener todas las mascotas del usuario", description = "Devuelve una lista de todas las mascotas del usuario autenticado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de mascotas del usuario devuelta correctamente",
                    content = @Content(schema = @Schema(implementation = MascotaVirtual.class)))
    })
    @GetMapping
    public ResponseEntity<List<MascotaVirtual>> getAllMascotas() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(mascotaService.getAllMascotas(userDetails));
    }
}
