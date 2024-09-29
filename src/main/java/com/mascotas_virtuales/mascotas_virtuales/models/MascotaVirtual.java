package com.mascotas_virtuales.mascotas_virtuales.models;


import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Random;

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


    private static final Random random = new Random();

    public MascotaVirtual(String nombre, TipoMascota tipoMascota, String color) {
        this.nombre = nombre;
        this.tipoMascota = tipoMascota;
        this.color = color;
        this.nivelEnergia = getRandomValue(50, 100);
        this.nivelHambre = getRandomValue(50, 100);
        this.nivelFelicidad = getRandomValue(50, 100);
    }


    private static int getRandomValue(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }
}
