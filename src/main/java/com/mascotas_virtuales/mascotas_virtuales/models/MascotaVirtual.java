package com.mascotas_virtuales.mascotas_virtuales.models;


import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MascotaVirtual {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre;

    @Enumerated(EnumType.STRING)
    private TipoMascota tipoMascota;

    private String color;
    private int nivelEnergia;
    private int nivelHambre;
    private int nivelFelicidad;

    @ManyToOne
    private Usuario propietario;
}
