package com.mascotas_virtuales.mascotas_virtuales.controllers;

import com.mascotas_virtuales.mascotas_virtuales.config.JwtTokenProvider;
import com.mascotas_virtuales.mascotas_virtuales.models.Usuario;
import com.mascotas_virtuales.mascotas_virtuales.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioService usuarioService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(AuthenticationManager authenticationManager, UsuarioService usuarioService, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.usuarioService = usuarioService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Operation(summary = "Iniciar sesión de usuario", description = "Autentica un usuario y devuelve los detalles del usuario autenticado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario autenticado correctamente",
                    content = @Content(schema = @Schema(implementation = UserDetails.class))),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario loginUser) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword())
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Generar el token JWT
        String token = jwtTokenProvider.createToken(userDetails.getUsername(), userDetails.getAuthorities().toString());

        // Devolver el token en la respuesta
        return ResponseEntity.ok(Map.of("token", token));

    }

    @Operation(summary = "Registrar un nuevo usuario", description = "Registra un nuevo usuario en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de usuario inválidos")
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Usuario newUser) {
        usuarioService.registerUser(newUser);
        return ResponseEntity.ok("Usuario registrado correctamente");
    }

}
