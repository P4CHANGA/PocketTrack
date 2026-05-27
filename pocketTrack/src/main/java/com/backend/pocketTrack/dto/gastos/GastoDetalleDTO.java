package com.backend.pocketTrack.dto.gastos;

import com.backend.pocketTrack.enums.TipoGasto;
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
    private TipoGasto tipo;

}
