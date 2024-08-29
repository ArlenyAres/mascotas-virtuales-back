package com.mascotas_virtuales.mascotas_virtuales.dto;


import com.mascotas_virtuales.mascotas_virtuales.models.TipoMascota;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MascotaPersonalizadaRequest {
    private TipoMascota tipoMascota;
    private String nombre;
    private String color;

}
