package com.backend.pocketTrack.dto.gastos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GastoDTO implements Serializable {
    private String nombre;
    private Double cantidad;
}
