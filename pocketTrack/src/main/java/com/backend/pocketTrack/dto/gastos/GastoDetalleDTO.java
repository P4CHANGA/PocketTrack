package com.backend.pocketTrack.dto.gastos;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GastoDetalleDTO implements Serializable {
    private long id;
    private String nombre;
    private Double cantidad;
}
