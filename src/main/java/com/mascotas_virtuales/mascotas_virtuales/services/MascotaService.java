package com.mascotas_virtuales.mascotas_virtuales.services;


import com.mascotas_virtuales.mascotas_virtuales.models.MascotaVirtual;
import com.mascotas_virtuales.mascotas_virtuales.models.Usuario;
import com.mascotas_virtuales.mascotas_virtuales.repositories.MascotaRepository;
import com.mascotas_virtuales.mascotas_virtuales.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MascotaService {

    private final MascotaRepository mascotaRepository;

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public MascotaService(MascotaRepository mascotaRepository, UsuarioRepository usuarioRepository) {

        this.mascotaRepository = mascotaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<MascotaVirtual> getAllMascotas(UserDetails userDetails) {

        if (userDetails.getAuthorities().toString().contains("ROLE_ADMIN")) {
            return mascotaRepository.findAll();
        } else {
            return mascotaRepository.findByPropietario_Username(userDetails.getUsername());
        }
    }

    public MascotaVirtual crearMascotaPersonalizada(Long mascotaId, String nombre, UserDetails userDetails) {

        // Buscar el tipo de mascota predefinida seleccionada
        MascotaVirtual mascotaPredefinida = mascotaRepository.findById(mascotaId)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));

        // Crear una nueva mascota basada en la predefinida, pero con los atributos personalizados
        MascotaVirtual nuevaMascota = new MascotaVirtual();
        nuevaMascota.setNombre(nombre); // Nombre personalizado
        nuevaMascota.setTipo(mascotaPredefinida.getTipo());
        nuevaMascota.setNivelEnergia(mascotaPredefinida.getNivelEnergia());
        nuevaMascota.setNivelHambre(mascotaPredefinida.getNivelHambre());
        nuevaMascota.setNivelFelicidad(mascotaPredefinida.getNivelFelicidad());

        // Asignar el propietario de la nueva mascota
        Usuario propietario = usuarioRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        nuevaMascota.setPropietario(propietario);

        // Guardar la nueva mascota en la base de datos
        return mascotaRepository.save(nuevaMascota);

    }

    public List<MascotaVirtual> getMascotasPredefinidas() {
        return mascotaRepository.findAll();
    }

}
