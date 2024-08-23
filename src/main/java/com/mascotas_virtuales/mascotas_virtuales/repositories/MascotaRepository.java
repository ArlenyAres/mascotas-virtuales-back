package com.mascotas_virtuales.mascotas_virtuales.repositories;

import com.mascotas_virtuales.mascotas_virtuales.models.MascotaVirtual;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MascotaRepository extends JpaRepository<MascotaVirtual, Long> {
    List<MascotaVirtual> findByPropietario_Username(String username);
}
