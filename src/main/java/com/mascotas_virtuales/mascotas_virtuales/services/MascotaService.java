package com.mascotas_virtuales.mascotas_virtuales.services;


import com.mascotas_virtuales.mascotas_virtuales.models.MascotaVirtual;
import com.mascotas_virtuales.mascotas_virtuales.models.TipoMascota;
import com.mascotas_virtuales.mascotas_virtuales.models.Usuario;
import com.mascotas_virtuales.mascotas_virtuales.repositories.MascotaRepository;
import com.mascotas_virtuales.mascotas_virtuales.repositories.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
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

    public MascotaVirtual crearMascotaPersonalizada(TipoMascota tipo, String nombre, String color, UserDetails userDetails) {

        MascotaVirtual nuevaMascota = new MascotaVirtual(nombre, tipo, color);

        // Buscar propietario basado en el userDetails
        Usuario propietario = usuarioRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        nuevaMascota.setPropietario(propietario);


        return mascotaRepository.save(nuevaMascota);
    }


    public List<MascotaVirtual> getMascotasPredefinidas() {
        return mascotaRepository.findAll();
    }


    public MascotaVirtual getMascotaById(Long mascotaId, UserDetails userDetails) {
       MascotaVirtual mascota = mascotaRepository.findById(mascotaId)
               .orElseThrow(() -> new EntityNotFoundException("Mascota no encontrada"));

       boolean isAdmin = userDetails.getAuthorities().stream()
               .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

         boolean isOwner = mascota.getPropietario().getUsername().equals(userDetails.getUsername());

         if (isAdmin || isOwner) {
             return mascota;
         } else {
             throw new AccessDeniedException("No tienes permisos para ver esta mascota");
         }
    }


    public MascotaVirtual updateMascota(Long mascotaId, MascotaVirtual mascotaActualizada, UserDetails userDetails) {

        MascotaVirtual mascotaExistente = mascotaRepository.findById(mascotaId)
                .orElseThrow(() -> new EntityNotFoundException("Mascota no encontrada"));

        boolean isAdmin = userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if(isAdmin || mascotaExistente.getPropietario().getUsername().equals(userDetails.getUsername())) {

            mascotaExistente.setNombre(mascotaActualizada.getNombre());
            mascotaExistente.setNivelEnergia(mascotaActualizada.getNivelEnergia());
            mascotaExistente.setNivelHambre(mascotaActualizada.getNivelHambre());
            mascotaExistente.setNivelFelicidad(mascotaActualizada.getNivelFelicidad());

            return mascotaRepository.save(mascotaExistente);
        } else {
            throw new AccessDeniedException("No tienes permiso para actualizar esta mascota");
        }

    }

    public void eliminarMascota(Long mascotaId, UserDetails userDetails) {
        MascotaVirtual mascota = mascotaRepository.findById(mascotaId)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));

        boolean isAdmin = userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        boolean isOwner = mascota.getPropietario() != null &&
                mascota.getPropietario().getUsername().equals(userDetails.getUsername());

        if (isAdmin || isOwner) {
            mascotaRepository.delete(mascota);
        } else {
            throw new RuntimeException("No tienes permisos para eliminar esta mascota");
        }
    }

}


