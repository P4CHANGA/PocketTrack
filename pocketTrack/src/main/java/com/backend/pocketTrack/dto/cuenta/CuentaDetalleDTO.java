package com.backend.pocketTrack.dto.cuenta;

import com.backend.pocketTrack.enums.Moneda;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CuentaDetalleDTO implements Serializable {
    private Moneda divisa;
    private Double cantidad;
    private String nombre;
}
