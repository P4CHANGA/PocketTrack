package com.backend.pocketTrack.dto.gastos;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateGastoDTO implements Serializable {

    private Long id;
    private String nombre;
    private Double cantidad;
    private Long cuentaId;
}
