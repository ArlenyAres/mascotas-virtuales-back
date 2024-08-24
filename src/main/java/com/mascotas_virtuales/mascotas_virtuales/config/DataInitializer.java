package com.mascotas_virtuales.mascotas_virtuales.config;

import com.mascotas_virtuales.mascotas_virtuales.models.MascotaVirtual;
import com.mascotas_virtuales.mascotas_virtuales.models.TipoMascota;
import com.mascotas_virtuales.mascotas_virtuales.models.Usuario;
import com.mascotas_virtuales.mascotas_virtuales.repositories.UsuarioRepository;
import com.mascotas_virtuales.mascotas_virtuales.repositories.MascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class DataInitializer {

    private final UsuarioRepository usuarioRepository;
    private final MascotaRepository mascotaRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(UsuarioRepository usuarioRepository, MascotaRepository mascotaRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.mascotaRepository = mascotaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner init() {
        return args -> {

            if (usuarioRepository.findByUsername("admin").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("adminpassword"));  // Codificar la contraseña
                admin.setEmail("admin@domain.com");
                admin.setRoles(Collections.singletonList("ROLE_ADMIN"));
                usuarioRepository.save(admin);
                System.out.println("Usuario administrador creado con éxito.");
            }


            if (mascotaRepository.count() == 0) {
                MascotaVirtual dragon = new MascotaVirtual(null, "Dragón", TipoMascota.DRAGON, "Rojo", 100, 100, 100, null);
                mascotaRepository.save(dragon);

                MascotaVirtual unicornio = new MascotaVirtual(null, "Unicornio", TipoMascota.UNICORNIO, "Blanco", 100, 100, 100, null);
                mascotaRepository.save(unicornio);

                MascotaVirtual extraterrestre = new MascotaVirtual(null, "Extraterrestre", TipoMascota.EXTRATERRESTRE, "Verde", 100, 100, 100, null);
                mascotaRepository.save(extraterrestre);

                MascotaVirtual fantasma = new MascotaVirtual(null, "Fantasma", TipoMascota.FANTASMA, "Transparente", 100, 100, 100, null);
                mascotaRepository.save(fantasma);

                System.out.println("Mascotas predeterminadas creadas con éxito.");
            }
        };
    }
}